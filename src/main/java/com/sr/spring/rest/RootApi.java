package com.sr.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/paginate")
    public Mono<Page<User>> paginate(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return service.paginate(page, size);
    }
}
