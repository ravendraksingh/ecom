package com.niyava.ecom.orderservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Ravendra Kumar Singh [ravendraksingh@gmail.com]
 * @version 1.0
 * @since 06 Dec 2023
 */

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidator.class)
@Documented
public @interface IsToday {
    String message() default "Date is not today's date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
