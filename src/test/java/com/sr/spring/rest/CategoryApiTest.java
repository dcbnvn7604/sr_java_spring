package com.sr.spring.rest;

import com.sr.spring.dto.CategoiesResponse;
import com.sr.spring.model.Category;
import com.sr.spring.model.Product;
import com.sr.spring.repository.CategoryRepository;
import com.sr.spring.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryApiTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void all() {
        Product product = new Product(1, "product1", "description11");
        productRepository.save(product);
        Category category = new Category(1, "category1", "description12");
        categoryRepository.save(category);
        product.addCategory(category);
        productRepository.save(product);
        product = new Product(2, "product2", "description21");
        productRepository.save(product);
        category = new Category(2, "category2", "description22");
        categoryRepository.save(category);
        product.addCategory(category);
        productRepository.save(product);
        ResponseEntity<CategoiesResponse> response = this.restTemplate.getForEntity("http://localhost:" + port + "/api/categories/lazy", CategoiesResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        CategoiesResponse lazyResponse = response.getBody();
        assertThat(lazyResponse.getCategories().get(0).getName()).isEqualTo("category1");
        assertThat(lazyResponse.getCategories().get(0).getProducts().get(0).getName()).isEqualTo("product1");

        response = this.restTemplate.getForEntity("http://localhost:" + port + "/api/categories/eager", CategoiesResponse.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        lazyResponse = response.getBody();
        assertThat(lazyResponse.getCategories().get(0).getName()).isEqualTo("category1");
        assertThat(lazyResponse.getCategories().get(0).getProducts().get(0).getName()).isEqualTo("product1");
    }

    @Test
    void transaction() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/api/categories/transaction", String.class);
        Category category = categoryRepository.findById(1L).orElse(null);
        assertThat(category).isNull();
    }
}
