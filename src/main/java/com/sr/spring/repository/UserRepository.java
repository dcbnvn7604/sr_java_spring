package com.sr.spring.repository;

import com.sr.spring.model.User;

import reactor.core.publisher.Flux;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    @Query("SELECT * FROM \"user\" ORDER BY id LIMIT :limit OFFSET :offset")
    public Flux<User> paginate(@Param("limit") int size, @Param("offset") int offset);
}
