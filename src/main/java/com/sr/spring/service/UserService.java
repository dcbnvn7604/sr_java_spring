package com.sr.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sr.spring.model.User;
import com.sr.spring.repository.UserRepository;

import reactor.core.publisher.Flux;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public Flux<User> findAll() {
        return repository.findAll();
    }
}
