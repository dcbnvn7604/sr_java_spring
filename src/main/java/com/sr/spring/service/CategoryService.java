package com.sr.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sr.spring.dto.SqlRecord;
import com.sr.spring.repository.CategoryRepository;

import reactor.core.publisher.Flux;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    public Flux<SqlRecord> sql() {
        return repository.sql();
    }
}
