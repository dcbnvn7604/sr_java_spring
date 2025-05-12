package com.sr.spring.rest;

import com.sr.spring.dto.LoginRequest;
import com.sr.spring.dto.LoginResponse;
import com.sr.spring.model.Job;
import com.sr.spring.model.User;
import com.sr.spring.repository.JobRepository;
import com.sr.spring.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@ActiveProfiles({"rest", "cli"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthApiTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JobRepository jobRepository;

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
	void queue() {
		this.restTemplate.getForObject("http://localhost:" + port + "/queue", String.class);
		List<Job> jobs =  jobRepository.findByStatus(1);
		assertThat(jobs).hasSize(1);
	}
}
