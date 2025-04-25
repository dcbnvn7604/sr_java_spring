package com.sr.spring.rest;

import com.sr.spring.dto.LoginRequest;
import com.sr.spring.dto.LoginResponse;
import com.sr.spring.dto.ValidateRequest;
import com.sr.spring.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/exception")
    public void exception() throws Exception {
        throw new Exception();
    }

    @PostMapping("/validate")
    public void validate(@RequestBody @Valid ValidateRequest request) {

    }
}
