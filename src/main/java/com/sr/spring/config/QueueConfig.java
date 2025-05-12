package com.sr.spring.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
    @Bean
    public Queue queue() {
        return new Queue("sr.queue", true);
    }

    @Bean
    public TopicExchange exhange() {
        return new TopicExchange("sr.topic");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exhange()).with("sr.routing");
    }
}
