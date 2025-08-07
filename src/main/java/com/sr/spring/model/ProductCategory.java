package com.sr.spring.model;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Table(name="product_category")
public class ProductCategory {
    private long productId;
    private long categoryId;
}
