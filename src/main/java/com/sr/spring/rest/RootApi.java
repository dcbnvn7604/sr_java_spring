package com.sr.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sr.spring.service.ProducerService;

@Profile("rest")
@RestController
public class RootApi {
    @Autowired
    private ProducerService producerService;

    @GetMapping("/health")
	public void health() {}

    @GetMapping("/queue")
    public void queue() {
        producerService.produce();
    }
}
