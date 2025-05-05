package com.sr.spring.repository;

import com.sr.spring.dto.JpqlRecord;
import com.sr.spring.model.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @EntityGraph(attributePaths = "products") // eager to specific query
    public List<Category> findAllByOrderById();

    @Query("select new com.sr.spring.dto.JpqlRecord(c.id, count(1)) from Category c  join c.products p where p.name is not null group by c.id")
    public List<JpqlRecord> jpql();

    @Query(value = "select c.id, count(1) as count from category as c" 
            + " inner join product_category as pc on pc.category_id = c.id" 
            + " inner join product as p on p.id = pc.product_id"
            + " where p.name is not null"
            + " group by c.id", nativeQuery = true)
    public List<JpqlRecord> sql();
}
