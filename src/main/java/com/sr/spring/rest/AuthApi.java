package com.sr.spring.rest;

import com.sr.spring.dto.LoginRequest;
import com.sr.spring.dto.LoginResponse;
import com.sr.spring.service.AuthService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthApi {
    @Autowired
    private AuthService authService;

    private Logger logger = LoggerFactory.getLogger(AuthApi.class);

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        logger.debug("Debugging log message");
        logger.info("Information log message");
        logger.warn("Warning log message");
        logger.error("Error log message");
        String token = authService.authen(loginRequest.getUsername(), loginRequest.getPassword());
        return new LoginResponse(token);
    }
}
