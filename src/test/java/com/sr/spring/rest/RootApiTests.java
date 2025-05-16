package com.sr.spring.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@WebFluxTest(RootApi.class)
class RootApiTests {
	@Autowired
	private WebTestClient client;

	@Test
	void health() {
		client.get().uri("/health")
			.exchange()
			.expectStatus().isOk();
	}
}
