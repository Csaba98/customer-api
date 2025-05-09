package com.customer.api.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.customer.api.validator.UniqueIdValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.FIELD;

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueIdValidator.class)
public @interface UniqueId {
    String message() default "Id must be unique!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
