package com.sr.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sr.spring.model.User;
import com.sr.spring.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RootApi {
    @Autowired
    private UserService service;

    @GetMapping("/health")
	public Mono<Void> health() {
        return Mono.empty();
    }

    @GetMapping("/users")
    public Flux<User> users() {
        return service.findAll();
    }

    @GetMapping("/parallel")
    public Mono<Void> parallel() {
        return service.parallel();
    }

    @GetMapping("/parallel_io")
    public Mono<Void> parallelIo() {
        return service.parallelIo();
    }

    @GetMapping("/parallel_cpu")
    public Mono<Void> parallelCpu() {
        return service.parallelCpu();
    }
}
