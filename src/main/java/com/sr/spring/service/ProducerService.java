package com.sr.spring.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.sr.spring.model.Job;
import com.sr.spring.repository.JobRepository;

@Service
@Profile("rest")
public class ProducerService {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private JobRepository jobRepository;

    public void produce() {
        Job job = new Job();
        job.setStatus(0);
        jobRepository.save(job);
        amqpTemplate.convertAndSend("sr.topic", "sr.routing", job.getId());
    }
}
