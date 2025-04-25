package com.sr.spring.dto.validate;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

@Constraint(validatedBy = PeriodValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Period {
    String message() default "invalid period";

    String first();
    String second();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

class PeriodValidator implements ConstraintValidator<Period, Object> {
    private String firstName;
    private String secondName;
    private String message;

    @Override
    public void initialize(Period annotation) {
        firstName = annotation.first();
        secondName = annotation.second();
        message = annotation.message();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        LocalDate first = (LocalDate) new BeanWrapperImpl(o).getPropertyValue(firstName);
        LocalDate second = (LocalDate) new BeanWrapperImpl(o).getPropertyValue(secondName);

        if (first.isAfter(second)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(secondName)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}