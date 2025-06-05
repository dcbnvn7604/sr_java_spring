package com.sr.spring.repository;

import com.sr.spring.model.User;

import reactor.core.publisher.Mono;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<Boolean> existsByUsername(String username);
}
