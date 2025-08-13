package com.sr.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sr.spring.dto.SqlRecord;
import com.sr.spring.model.User;
import com.sr.spring.service.CategoryService;
import com.sr.spring.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RootApi {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/health")
	public Mono<Void> health() {
        return Mono.empty();
    }

    @GetMapping("/users")
    public Flux<User> users() {
        return userService.findAll();
    }

    @GetMapping("/sql")
    public Flux<SqlRecord> sql() {
        return categoryService.sql();
    }

    @GetMapping("/rollback")
    public Mono<Void> rollback() {
        return categoryService.rollback();
    }

    @GetMapping("/transaction")
    public Mono<Void> transaction() {
        return categoryService.transaction();
    }
}
