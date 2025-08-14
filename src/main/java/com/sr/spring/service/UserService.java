package com.sr.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    public Mono<Page<User>> paginate(int page, int size) {
        Mono<Long> count = repository.count();
        Flux<User> users = repository.paginate(size, page * size);
        return count.zipWith(users.collectList(), (total, userList) -> {
            return new PageImpl<>(userList, PageRequest.of(page, size), total);
        });
    }
}
