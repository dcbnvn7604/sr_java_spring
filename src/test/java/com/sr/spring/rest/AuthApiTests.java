package com.sr.spring.rest;

import com.sr.spring.dto.LoginRequest;
import com.sr.spring.dto.LoginResponse;
import com.sr.spring.model.User;
import com.sr.spring.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthApiTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	void login() {
		User user = new User("user", passwordEncoder.encode("password"));
		userRepository.save(user);
		LoginRequest request = new LoginRequest();
		request.setUsername("user");
		request.setPassword("password");
		ResponseEntity<LoginResponse> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/auth/login", request, LoginResponse.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + response.getBody().getToken());
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response1 = this.restTemplate.exchange("http://localhost:" + port + "/api/auth/secure", HttpMethod.GET, entity, String.class);
		assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
