package com.sr.spring.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateRequest {
    @Min(value = 1, message = "greater than 0")
    private int number;
    @NotNull
    @NotBlank
    private String username;
}
