package com.sr.spring.service;

import com.sr.spring.model.Category;
import com.sr.spring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> all() {
        return categoryRepository.findAll();
    }

    public List<Category> allEager() {
        return categoryRepository.findAllByOrderById();
    }

    @Transactional
    public void transaction() {
        Category category = new Category(1, "category1", "description1");
        categoryRepository.save(category);
        throw new RuntimeException("ex");
    }
}
