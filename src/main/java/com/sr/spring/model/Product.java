package com.sr.spring.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Getter
public class Product {
    @Setter
    @Id
    private long id;
    private String name;
    private String description;
    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    public Product(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void addCategory(Category category) {
        categories.add(category);
    }
}
