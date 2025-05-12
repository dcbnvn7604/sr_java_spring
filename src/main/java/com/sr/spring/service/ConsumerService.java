package com.sr.spring.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.sr.spring.model.Job;
import com.sr.spring.repository.JobRepository;

@Component
@Profile("cli")
public class ConsumerService {
    @Autowired
    private JobRepository jobRepository;

    @RabbitListener(queues = "sr.queue")
    public void consume(Long id) {
        Job job = jobRepository.findById(id).orElseThrow();
        job.setStatus(1);
        jobRepository.save(job);
    }
}
