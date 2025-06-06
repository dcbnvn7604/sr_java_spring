package com.sr.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sr.spring.model.User;
import com.sr.spring.repository.UserRepository;
import com.sr.spring.security.Jwt;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Jwt jwt;

    public Flux<User> findAll() {
        return repository.findAll();
    }

    public Mono<String> auth(String username, String password) {
        return repository.findByUsername(username)
            .map(user -> {
                if (passwordEncoder.matches(password, user.getPassword())) {
                    return jwt.generateToken(user.getUsername());
                }
                return null;
            });
    }

}
