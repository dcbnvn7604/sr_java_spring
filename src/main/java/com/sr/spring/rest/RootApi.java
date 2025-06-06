package com.sr.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sr.spring.model.User;
import com.sr.spring.service.UserService;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @PostMapping("/auth")
    public Mono<AuthenticationResponse> authen(@RequestBody AuthenticationRequest request) {
        return service.auth(request.getUsername(), request.getPassword())
            .map(token -> {
                AuthenticationResponse response = new AuthenticationResponse();
                response.setToken(token);
                return response;
            });
    }

    @GetMapping("/secure")
    public Mono<Void> secure() {
        return Mono.empty();
    }

    @GetMapping("/admin")
    public Mono<Void> admin() {
        return Mono.empty();
    }
}

@Getter
@Setter
@NoArgsConstructor
class AuthenticationResponse {
    private String token;
}

@Setter
@Getter
@NoArgsConstructor
class AuthenticationRequest {
    private String username;
    private String password;
}