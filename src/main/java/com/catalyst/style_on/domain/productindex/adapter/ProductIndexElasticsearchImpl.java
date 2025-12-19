package com.catalyst.style_on.domain.productindex.adapter;

import com.catalyst.style_on.domain.productindex.ProductIndex;
import com.catalyst.style_on.domain.productindex.ProductIndexService;
import com.catalyst.style_on.domain.productindex.dto.ProductIndexSearchParamsDTO;
import com.catalyst.style_on.domain.shared.json.JsonUtils;
import com.catalyst.style_on.domain.shared.price.Price;
import com.catalyst.style_on.exception.InternalServerError;
import com.catalyst.style_on.exception.NotFoundException;
import com.catalyst.style_on.infrastructure.elasticsearch.ElasticsearchConfig;
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
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.util.Arrays;
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

    private static final String[] SOURCE_INCLUDE_FIELDS = List.of(
            "sku",
            "name",
            "images",
            "brand_code"
    ).toArray(String[]::new);

    private final ElasticsearchConfig cfg;
    private final RestHighLevelClient client;


    @Override
    public Flux<ProductIndex> searchProducts(ProductIndexSearchParamsDTO params) {
        log.info("Elastic config: {}", cfg);

        return Mono.fromCallable(() -> {
                    SearchSourceBuilder searchSource = buildSearchSource(params);

                    searchSource.fetchSource(SOURCE_INCLUDE_FIELDS, null);
                    searchSource.from(0);
                    searchSource.size(5);

                    SearchRequest searchRequest = new SearchRequest(cfg.getIndexProduct());
                    searchRequest.source(searchSource);

                    log.info("Search: {}", searchRequest);

                    return client.search(searchRequest, RequestOptions.DEFAULT);
                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapMany(this::processSearchResponse);
    }

    @Override
    public Mono<ProductIndex> findBySku(String sku) {
        return Mono.fromCallable(() -> {
                    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                            .filter(QueryBuilders.termQuery("sku.keyword", sku));

                    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
                    searchSourceBuilder.query(queryBuilder);
                    searchSourceBuilder.size(1);

                    SearchRequest searchRequest = new SearchRequest(cfg.getIndexProduct());
                    searchRequest.source(searchSourceBuilder);

                    return client.search(searchRequest, RequestOptions.DEFAULT);
                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(this::processSingleHitResponse);
    }

    private Flux<ProductIndex> processSearchResponse(SearchResponse searchResponse) {
        return Mono.just(searchResponse)
                .filter(response -> RestStatus.OK.equals(response.status()))
                .switchIfEmpty(Mono.error(new InternalServerError("Elasticsearch returned non-OK status: " + searchResponse.status())))
                .flatMapIterable(filteredSearchResponse -> {
                    SearchHits hits = filteredSearchResponse.getHits();
                    return hits == null ? Collections.emptyList() : Arrays.asList(hits.getHits());
                })
                .flatMapSequential(hit -> JsonUtils.reactiveDeserialize(hit.getSourceAsString(), ProductIndex.class));
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

    private Mono<ProductIndex> processSingleHitResponse(SearchResponse searchResponse) {
        return Mono.just(searchResponse)
                .filter(response -> RestStatus.OK.equals(response.status()))
                .switchIfEmpty(Mono.error(new InternalServerError("Elasticsearch returned non-OK status: " + searchResponse.status())))
                .map(SearchResponse::getHits)
                .filter(hits -> hits != null && hits.getHits().length > 0)
                .map(hits -> hits.getAt(0))
                .map(SearchHit::getSourceAsString)
                .flatMap(str -> JsonUtils.reactiveDeserialize(str, ProductIndex.class))
                .switchIfEmpty(Mono.error(new NotFoundException("Product not found by SKU")));
    }

}
