package com.sr.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sr.spring.model.User;
import com.sr.spring.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public Flux<User> findAll() {
        return repository.findAll();
    }

    public Mono<Void> validate(String username) {
        return repository.existsByUsername(username)
            .flatMap(exists -> {
                if (exists) {
                    return Mono.empty();
                }
                return Mono.error(new IllegalArgumentException("Username not exists"));
            });
    }
}
