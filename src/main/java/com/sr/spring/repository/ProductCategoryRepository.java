package com.sr.spring.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.sr.spring.model.ProductCategory;

public interface ProductCategoryRepository extends ReactiveCrudRepository<ProductCategory, Long>  {
    
}
