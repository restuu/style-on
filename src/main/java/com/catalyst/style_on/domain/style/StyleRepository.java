package com.catalyst.style_on.domain.style;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface StyleRepository extends R2dbcRepository<Style, Long> {
    @Query("""
            select s.*
            from style s
            join style_tag st on st.style_id = s.id
            join tag t on t.id = st.tag_id
            where t.name = $1
            """)
    Flux<Style> findByTagName(String tagName);
}
