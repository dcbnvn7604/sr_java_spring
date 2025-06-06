package com.sr.spring.rest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import com.sr.spring.model.User;
import com.sr.spring.repository.UserRepository;

import lombok.Getter;
import lombok.NoArgsConstructor;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@BeforeAll
	public void setup() {
		String sql = """
			DROP TABLE IF EXISTS "user";
			CREATE TABLE IF NOT EXISTS "user" (
				id SERIAL PRIMARY KEY,
				username VARCHAR(20) NOT NULL,
				password VARCHAR(60) NOT NULL
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
	void secure() {
		User user = new User("user", passwordEncoder.encode("password"));
		repository.save(user)
			.block();
		AuthenRequest request = new AuthenRequest();
		request.setUsername("user");
		request.setPassword("password");
		ResponseEntity<AuthenResponse> response = this.restTemplate.postForEntity("http://localhost:" + port + "/auth", request, AuthenResponse.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + response.getBody().getToken());
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response1 = this.restTemplate.exchange("http://localhost:" + port + "/secure", HttpMethod.GET, entity, String.class);
		assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}

@Getter
@Setter
@NoArgsConstructor
class AuthenRequest {
	private String username;
	private String password;
}

@Getter
@Setter
@NoArgsConstructor
class AuthenResponse {
	private String token;
}