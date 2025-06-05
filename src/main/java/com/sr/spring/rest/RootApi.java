package com.sr.spring.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sr.spring.dto.ErrorResponse;
import com.sr.spring.dto.ValidateRequest;
import com.sr.spring.exception.SRValidationError;
import com.sr.spring.model.User;
import com.sr.spring.service.UserService;

import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RootApi {
    @Autowired
    private UserService service;

    @GetMapping("/health")
	public Mono<Void> health() {
        return Mono.empty();
    }

    @GetMapping("/users")
    public Flux<User> users() {
        return service.findAll();
    }

    @PostMapping("/validate")
    public Mono<Void> validate(@RequestBody @Valid ValidateRequest request) {
        return service.validate(request.getUsername())
            .onErrorMap(error -> {
                if (error instanceof IllegalArgumentException) {
                    SRValidationError validationError = new SRValidationError();
                    validationError.setMessage(error.getMessage());
                    validationError.setField("username");
                    return validationError;
                }
                return error;
            });
    }
}
