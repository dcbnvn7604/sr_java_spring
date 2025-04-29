package com.sr.spring.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CategoiesResponse {
    private List<CategoryInLazy> categories;

    @NoArgsConstructor
    @Getter
    @Setter
    public static class CategoryInLazy {
        private long id;
        private String name;
        private List<ProductInLazy> products;
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class ProductInLazy {
        private long id;
        private String name;
    }
}
