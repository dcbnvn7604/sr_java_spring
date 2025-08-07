package com.sr.spring.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.sr.spring.model.Product;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {}
