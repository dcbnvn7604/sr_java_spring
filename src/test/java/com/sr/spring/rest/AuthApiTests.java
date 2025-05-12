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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthApiTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserRepository userRepository;

	@Test
	void login() {
		User user = new User("user", "password");
		userRepository.save(user);
		LoginRequest request = new LoginRequest();
		request.setUsername("user");
		request.setPassword("password");
		LoginResponse response = this.restTemplate.postForObject("http://localhost:" + port + "/api/auth/login", request, LoginResponse.class);
		assertThat(response.getToken()).contains("token");
	}

	@Test
	void i18n() {
		String response = this.restTemplate.getForObject("http://localhost:" + port + "/api/auth/i18n", String.class);
		assertThat(response).isEqualTo("Message 1 in English");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept-Language", "ja");
		HttpEntity<String> entity = new HttpEntity<>(headers);
		String responseJa = this.restTemplate.exchange("http://localhost:" + port + "/api/auth/i18n", HttpMethod.GET, entity, String.class).getBody();
		assertThat(responseJa).isEqualTo("日本語のメッセージ1");
	}
}
