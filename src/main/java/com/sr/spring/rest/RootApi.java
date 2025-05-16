package com.sr.spring.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class RootApi {
    @GetMapping("/health")
	public Mono<Void> health() {
        return Mono.empty();
    }
}
