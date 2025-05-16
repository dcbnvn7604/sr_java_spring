package com.sr.spring.rest;

import com.sr.spring.dto.LoginRequest;
import com.sr.spring.dto.LoginResponse;
import com.sr.spring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthApi {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authen(loginRequest.getUsername(), loginRequest.getPassword());
        return new LoginResponse(token);
    }

    @GetMapping("/parallel")
    public void parallel() {
        authService.parallel();
    }
}
