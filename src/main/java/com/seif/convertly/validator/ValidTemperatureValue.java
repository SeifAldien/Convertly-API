package com.seif.convertly.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TemperatureValueValidator.class)
public @interface ValidTemperatureValue {
    String message() default "Invalid value for the selected category";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
