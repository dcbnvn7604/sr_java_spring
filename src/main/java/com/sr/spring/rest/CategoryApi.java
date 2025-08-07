package com.sr.spring.rest;

import com.sr.spring.dto.CategoiesResponse;
import com.sr.spring.model.Category;
import com.sr.spring.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryApi {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/lazy")
    public CategoiesResponse allLazy() {
        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        class Data {
            private List<Category> categories;
        }

        List<Category> categories = categoryService.all();
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(new Data(categories), CategoiesResponse.class);
    }

    @GetMapping("/eager")
    public CategoiesResponse allEager() {
        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        class Data {
            private List<Category> categories;
        }
        List<Category> categories = categoryService.allEager();
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(new Data(categories), CategoiesResponse.class);
    }

    @GetMapping("/rollback")
    public void rollback() {
        categoryService.rollback();
    }

    @GetMapping("/transaction")
    public void transaction() {
        categoryService.transaction();
    }
}
