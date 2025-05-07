package com.sr.spring.service;

import com.sr.spring.model.User;
import com.sr.spring.repository.UserRRepository;
import com.sr.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRRepository userRRepository;

    public String authen(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user.getPassword().equals(password)) {
            return "token";
        }
        return "";
    }

    public Page<User> list(int page, int size) {
        return userRRepository.findAll(PageRequest.of(page, size));
    }
}
