package com.sr.spring.service;

import com.sr.spring.model.Category;
import com.sr.spring.model.Product;
import com.sr.spring.repository.CategoryRepository;
import com.sr.spring.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository pRepository;

    public List<Category> all() {
        return categoryRepository.findAll();
    }

    public List<Category> allEager() {
        return categoryRepository.findAllByOrderById();
    }

    @Transactional
    public void rollback() {
        Category category = new Category(1, "category1", "description1");
        categoryRepository.save(category);
        throw new RuntimeException("ex");
    }

    @Transactional
    public void transaction() {
        Category category = new Category();
        category.setName("category1");
        category.setDescription("description1");
        categoryRepository.save(category);
        Product product = new Product();
        product.setName("product1");
        product.setDescription("pdescription1");
        product.addCategory(category);
        pRepository.save(product);
    }
}
