package com.sr.spring.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sr.spring.model.User;
import com.sr.spring.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public Flux<User> findAll() {
        return repository.findAll();
    }

    public Mono<Void> parallel() {
        return Mono.zip(
            task("task1"),
            task("task2")
        ).then(Mono.empty());
    }

    private Mono<String> task(String task) {
        return Mono.fromCallable(() -> print(task, "1"))
            .delayElement(Duration.ofMillis(1000))
            .then(
                Mono.fromCallable(() -> print(task, "2"))
                .delayElement(Duration.ofMillis(1000))
            ).then(
                Mono.fromCallable(() -> print(task, "3"))
                .delayElement(Duration.ofMillis(1000))
            );
    }

    private String print(String task, String index) {
        System.out.println(task + index);
        return task + index;
    }

    public Mono<Void> parallelIo() {
        return Mono.zip(
            Mono.fromCallable(() -> taskThread("taskio1"))
                .subscribeOn(Schedulers.boundedElastic()),
            Mono.fromCallable(() -> taskThread("taskio2"))
                .subscribeOn(Schedulers.boundedElastic())
        ).then(Mono.empty());
    }

    private String taskThread(String task) {
        try {
            System.out.println(task + "1");
            Thread.sleep(1000);
            System.out.println(task + "2");
            Thread.sleep(1000);
            System.out.println(task + "3");
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        return task;
    }

    public Mono<Void> parallelCpu() {
        return Mono.zip(
            Mono.fromCallable(() -> taskThread("taskcpu1"))
                .subscribeOn(Schedulers.parallel()),
            Mono.fromCallable(() -> taskThread("taskcpu2"))
                .subscribeOn(Schedulers.parallel())
        ).then(Mono.empty());
    }
}
