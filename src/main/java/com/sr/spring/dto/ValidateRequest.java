package com.sr.spring.dto;

import com.sr.spring.dto.validate.Period;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Period(first = "startDate", second = "endDate")
public class ValidateRequest {
    @NotNull
    @Valid
    private SRObject object;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}

@Setter
@Getter
class SRObject {
    @Min(value = 1, message = "greater than 0")
    private int number;

    @NotNull
    private LocalDate date;
}