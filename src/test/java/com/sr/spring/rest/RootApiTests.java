package com.sr.spring.rest;

import org.junit.jupiter.api.BeforeAll;
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

import com.sr.spring.model.User;
import com.sr.spring.repository.UserRepository;

import lombok.Getter;
import lombok.Setter;

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

	@BeforeAll
	public void setup() {
		String sql = """
			DROP TABLE IF EXISTS "user";
			CREATE TABLE IF NOT EXISTS "user" (
				id SERIAL PRIMARY KEY,
				username VARCHAR(20) NOT NULL,
				password VARCHAR(20) NOT NULL
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
	void validate() {
		ValidateRequest request = new ValidateRequest();
		request.setNumber(-1);
		request.setUsername("testuser");
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/validate", request, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).contains("greater than 0");
	}

	@Test
	void validateUsernameNotExists() {
		ValidateRequest request = new ValidateRequest();
		request.setNumber(1);
		request.setUsername("nonexistentuser");
		ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/validate", request, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(response.getBody()).contains("Username not exists");
	}
}

@Getter
@Setter
class ValidateRequest {
	private int number;
	private String username;
}
