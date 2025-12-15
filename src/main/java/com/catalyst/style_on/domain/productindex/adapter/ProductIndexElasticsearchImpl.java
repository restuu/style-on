package com.catalyst.style_on.domain.productindex.adapter;

import com.catalyst.style_on.domain.productindex.ProductIndex;
import com.catalyst.style_on.domain.productindex.ProductIndexService;
import com.catalyst.style_on.domain.productindex.dto.ProductIndexSearchParamsDTO;
import com.catalyst.style_on.domain.shared.price.Price;
import com.catalyst.style_on.exception.InternalServerError;
import com.catalyst.style_on.infrastructure.elasticsearch.ElasticsearchConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;

import static org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder.FilterFunctionBuilder;

import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductIndexElasticsearchImpl implements ProductIndexService {

    private static final String PRICE_FIELD = "price.price";
    private static final String STYLE_FIELD = "style";
    private static final String MOVEMENT_FIELD = "movement";
    private static final String STRAP_MATERIAL_FIELD = "strap.keyword";
    private static final String COLOR_FIELD = "color.keyword";

    private final ElasticsearchConfig cfg;
    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;


    @Override
    public Flux<ProductIndex> searchProducts(ProductIndexSearchParamsDTO params) {
        log.info("Elastic config: {}", cfg);

        return Mono.<SearchResponse>create(sink -> {
                    SearchSourceBuilder searchSource = buildSearchSource(params);

                    String[] includeFields = List.of("sku", "name").toArray(String[]::new);
                    searchSource.fetchSource(includeFields, null);
                    searchSource.from(0);
                    searchSource.size(5);

                    SearchRequest searchRequest = new SearchRequest();
                    searchRequest.indices(cfg.getIndexProduct());
                    searchRequest.source(searchSource);

                    log.info("Search: {}", searchRequest);

                    try {
                        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
                        sink.success(response);
                    } catch (IOException e) {
                        sink.error(e);
                    }
                })
                .flatMapIterable(searchResponse -> {
                    if (!RestStatus.OK.equals(searchResponse.status())) {
                        String builder = "Error searching products: " +
                                searchResponse.status().toString();

                        throw new InternalServerError(builder);
                    }

                    List<ProductIndex> productIndexes = new ArrayList<>();
                    for (SearchHit hit : searchResponse.getHits()) {
                        try {
                            productIndexes.add(objectMapper.readValue(hit.getSourceAsString(), ProductIndex.class));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    return productIndexes;
                });
    }

    private SearchSourceBuilder buildSearchSource(ProductIndexSearchParamsDTO params) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.filter(QueryBuilders.termQuery("publish", true));

        List<FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();

        filterFunctionBuilders.addAll(buildPriceFunctionFilterScore(params.getPrice()));
        filterFunctionBuilders.addAll(buildStyleFunctionFilterScore(params.getKeyStyle()));
        filterFunctionBuilders.addAll(buildMovementFunctionFilterScore(params.getMovement()));
        filterFunctionBuilders.addAll(buildStrapMaterialFunctionFilterScore(params.getStrapMaterial()));
        filterFunctionBuilders.addAll(buildColorFunctionFilterScore(params.getColor()));

        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(
                        boolQuery,
                        filterFunctionBuilders.toArray(FilterFunctionBuilder[]::new)
                )
                .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                .boostMode(CombineFunction.REPLACE);
        // .boostMode(CombineFunction.MAX);

        searchSourceBuilder.query(functionScoreQueryBuilder);

        return searchSourceBuilder;
    }

    private List<FilterFunctionBuilder> buildColorFunctionFilterScore(Map<String, Integer> colorScores) {
        return colorScores.keySet().stream().map(colorCode -> {
                    Integer weight = colorScores.get(colorCode);

                    QueryBuilder strapFilter = QueryBuilders.termQuery(COLOR_FIELD, colorCode);

                    return new FilterFunctionBuilder(strapFilter, ScoreFunctionBuilders.weightFactorFunction(weight));
                })
                .collect(Collectors.toList());
    }

    private List<FilterFunctionBuilder> buildStrapMaterialFunctionFilterScore(Map<String, Integer> strapScores) {
        return strapScores.keySet().stream().map(strapCode -> {
                    Integer weight = strapScores.get(strapCode);

                    QueryBuilder strapFilter = QueryBuilders.termQuery(STRAP_MATERIAL_FIELD, strapCode);

                    return new FilterFunctionBuilder(strapFilter, ScoreFunctionBuilders.weightFactorFunction(weight));
                })
                .collect(Collectors.toList());
    }

    private List<FilterFunctionBuilder> buildMovementFunctionFilterScore(Map<Integer, Integer> movementScores) {
        return movementScores.keySet().stream().map(movementId -> {
                    Integer weight = movementScores.get(movementId);

                    QueryBuilder styleFilter = QueryBuilders.termQuery(MOVEMENT_FIELD, movementId);

                    return new FilterFunctionBuilder(styleFilter, ScoreFunctionBuilders.weightFactorFunction(weight));
                })
                .collect(Collectors.toList());
    }

    private List<FilterFunctionBuilder> buildStyleFunctionFilterScore(Map<Integer, Integer> styleScores) {
        return styleScores.keySet().stream().map(styleId -> {
                    Integer weight = styleScores.get(styleId);

                    QueryBuilder styleFilter = QueryBuilders.termQuery(STYLE_FIELD, styleId);

                    return new FilterFunctionBuilder(styleFilter, ScoreFunctionBuilders.weightFactorFunction(weight));
                })
                .collect(Collectors.toList());
    }

    private RangeQueryBuilder buildPriceRangeFilter(Price price) {
        RangeQueryBuilder range = QueryBuilders.rangeQuery(PRICE_FIELD);
        BigDecimal min = price.min();
        BigDecimal max = price.max();

        if (min.compareTo(BigDecimal.ZERO) > 0) {
            range = range.gte(min);
        }

        if (max.compareTo(BigDecimal.ZERO) > 0) {
            range = range.lte(max);
        }

        return range;
    }

    private List<FilterFunctionBuilder> buildPriceFunctionFilterScore(Map<Price, Integer> price) {
        return price.keySet().stream()
                .map(key -> {

                    RangeQueryBuilder range = buildPriceRangeFilter(key);

                    float weight = price.get(key);

                    return new FilterFunctionBuilder(range, ScoreFunctionBuilders.weightFactorFunction(weight));
                })
                .collect(Collectors.toList());
    }

}
