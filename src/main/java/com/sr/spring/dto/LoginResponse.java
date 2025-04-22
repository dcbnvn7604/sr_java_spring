package com.sr.spring.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginResponse {
    @NonNull private String token;
}
