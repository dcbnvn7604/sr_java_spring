package com.sr.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sr.spring.dto.SqlRecord;
import com.sr.spring.model.Category;
import com.sr.spring.model.Product;
import com.sr.spring.model.ProductCategory;
import com.sr.spring.repository.CategoryRepository;
import com.sr.spring.repository.ProductCategoryRepository;
import com.sr.spring.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository cRepository;

    @Autowired
    private ProductRepository pRepository;

    @Autowired
    private ProductCategoryRepository pcRepository;

    public Flux<SqlRecord> sql() {
        return cRepository.sql();
    }

    @Transactional
    public Mono<Void> rollback() {
        Category category = new Category();
        category.setName("category1");
        category.setDescription("cdescription1");
        return cRepository.save(category)
            .then(Mono.error(new RuntimeException("ex")))
            .then();
    }

    @Transactional
    public Mono<Void> transaction() {
        Category category = new Category();
		category.setName("Test Category");
		category.setDescription("This is a test category");
		Mono<Long> categoryIdMono = cRepository.save(category).map(Category::getId);
		Product product1 = new Product();
		product1.setName("Test Product");
		product1.setDescription("This is a test product");
        Mono<Long> productIdMono = pRepository.save(product1).map(Product::getId);
        return Mono.zip(categoryIdMono, productIdMono)
            .flatMap(tuple -> {
                ProductCategory productCategory = new ProductCategory();
				productCategory.setCategoryId(tuple.getT1());
				productCategory.setProductId(tuple.getT2());
                return pcRepository.save(productCategory);
            }).then();
    }
}
