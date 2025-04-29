package com.sr.spring.repository;

import com.sr.spring.model.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @EntityGraph(attributePaths = "products") // eager to specific query
    public List<Category> findAllByOrderById();
}
