package com.sr.spring.dto.validate;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Autowired;

import com.sr.spring.repository.UserRepository;

import java.lang.annotation.ElementType;
import jakarta.validation.Constraint;
import java.lang.annotation.RetentionPolicy;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Constraint(validatedBy = UsernameExistsValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameExists {
    String message() default "username not exists";
    Class<?>[] groups() default {};
    Class<? extends jakarta.validation.Payload>[] payload() default {};
}

class UsernameExistsValidator implements ConstraintValidator<UsernameExists, String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null || username.isEmpty()) {
            return true;
        }
        return userRepository.existsByUsername(username);
    }
}