package com.niyava.ecom.orderservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

/**
 * @author Ravendra Kumar Singh [ravendraksingh@gmail.com]
 * @version 1.0
 * @since 06 Dec 2023
 */
public class DateValidator implements ConstraintValidator<IsToday, LocalDate> {

    @Override
    public void initialize(IsToday constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value.isEqual(LocalDate.now());
    }
}
