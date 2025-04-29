package com.sr.spring.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Getter
public class Category {
    @Setter
    @Id
    private long id;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "categories")
    private Set<Product> products = new HashSet<>();

    public Category(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
