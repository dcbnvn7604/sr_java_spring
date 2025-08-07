package com.sr.spring.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import com.sr.spring.model.Category;
import com.sr.spring.dto.SqlRecord;

public interface CategoryRepository extends ReactiveCrudRepository<Category, Long> {
    @Query("select c.id, count(1) as count from category as c"
        + " inner join product_category as pc on pc.category_id = c.id"
        + " inner join product as p on p.id = pc.product_id"
        + " where p.name is not null"
        + " group by c.id"
    )
    Flux<SqlRecord> sql();
}
