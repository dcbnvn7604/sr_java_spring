package com.sr.spring.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.r2dbc.core.DatabaseClient;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import com.sr.spring.model.Category;
import com.sr.spring.model.Product;
import com.sr.spring.model.ProductCategory;
import com.sr.spring.model.User;
import com.sr.spring.repository.CategoryRepository;
import com.sr.spring.repository.ProductCategoryRepository;
import com.sr.spring.repository.ProductRepository;
import com.sr.spring.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class RootApiTests {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private DatabaseClient client;

	@Autowired
	private UserRepository repository;

	@Autowired
	private CategoryRepository cRepository;

	@Autowired
	private ProductRepository pRepository;

	@Autowired
	private ProductCategoryRepository pcRepository;

	@BeforeEach
	public void setup() {
		String sql = """
			DROP TABLE IF EXISTS "user";
			CREATE TABLE IF NOT EXISTS "user" (
				id SERIAL PRIMARY KEY,
				username VARCHAR(20) NOT NULL,
				password VARCHAR(20) NOT NULL
			);
			DROP TABLE IF EXISTS product;
			CREATE TABLE IF NOT EXISTS product (
				id SERIAL PRIMARY KEY,
				name VARCHAR(50) NOT NULL,
				description VARCHAR(255)
			);
			DROP TABLE IF EXISTS category;
			CREATE TABLE IF NOT EXISTS category (
				id SERIAL PRIMARY KEY,
				name VARCHAR(50) NOT NULL,
				description VARCHAR(255)
			);
			DROP TABLE IF EXISTS product_category;
			CREATE TABLE IF NOT EXISTS product_category (
				product_id BIGINT NOT NULL,
				category_id BIGINT NOT NULL,
				PRIMARY KEY (product_id, category_id)
			);
			""";
		client.sql(sql)
			.then()
			.block();
	}

	@Test
	void health() {
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/health", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void users() {
		User user = new User("user", "password");
		repository.save(user)
			.block();
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/users", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("[{\"username\":\"user\",\"password\":\"password\"}]");
	}

	@Test
	void sql() {
		Category category = new Category();
		category.setName("Test Category");
		category.setDescription("This is a test category");
		Mono<Long> categoryIdMono = cRepository.save(category).map(Category::getId);
		Product product1 = new Product();
		product1.setName("Test Product");
		product1.setDescription("This is a test product");
		Product product2 = new Product();
		product2.setName("Test Product 2");
		product2.setDescription("This is a test product 2");
		Flux<Long> productIdFlux = pRepository.saveAll(List.of(product1, product2)).map(Product::getId);
		categoryIdMono.flatMapMany(categoryId -> productIdFlux.map(productId -> Tuples.of(categoryId, productId)))
			.map(tuple -> {
				ProductCategory productCategory = new ProductCategory();
				productCategory.setCategoryId(tuple.getT1());
				productCategory.setProductId(tuple.getT2());
				return productCategory;
			})
			.collectList()
			.flatMap(productCategories -> {
				return pcRepository.saveAll(productCategories)
					.collectList();
			})
			.block();
		ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/sql", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo("[{\"id\":1,\"count\":2}]");
	}

	@Test
	void rollback() {
		long total = cRepository.count()
			.block();
		assertThat(total).isEqualTo(0);
		this.restTemplate.getForEntity("http://localhost:" + port + "/rollback", String.class);
		total = cRepository.count()
			.block();
		assertThat(total).isEqualTo(0);
	}

	@Test
	void transaction() {
		long total = cRepository.count()
			.block();
		assertThat(total).isEqualTo(0);
		this.restTemplate.getForEntity("http://localhost:" + port + "/transaction", String.class);
		total = cRepository.count()
			.block();
		assertThat(total).isEqualTo(1);
	}
}
