package com.sr.spring.service;

import com.sr.spring.model.User;
import com.sr.spring.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public String authen(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user.getPassword().equals(password)) {
            return "token";
        }
        return "";
    }

    public void parallel() {
        ForkJoinPool pool = new ForkJoinPool();
        Shared shared = new Shared("shared");
        List<RecursiveTask<List<String>>> tasks = List.of(
            new Task("task1", shared),
            new Task("task2", shared)
        );
        for (RecursiveTask<List<String>> task: tasks) {
            pool.submit(task);
        }
        for (RecursiveTask<List<String>> task: tasks) {
            List<String> result = task.join();
        }
        pool.shutdown();
    }
}

@AllArgsConstructor
class Task extends RecursiveTask<List<String>> {
    private String name;
    private Shared shared;

    @Override
    protected List<String> compute() {
        shared.setName(shared.getName() + name);
        try {
            System.out.println(shared.getName() + name + "0");
            Thread.sleep(1000);
            System.out.println(shared.getName() + name + "1");
            Thread.sleep(1000);
            System.out.println(shared.getName() + name + "2");
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
        return new ArrayList<String>();
    }
}

@AllArgsConstructor
@Getter
@Setter
class Shared {
    private String name;
}
