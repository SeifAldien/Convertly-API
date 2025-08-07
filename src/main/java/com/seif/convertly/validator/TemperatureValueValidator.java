package com.seif.convertly.validator;

import com.seif.convertly.enums.Category;
import com.seif.convertly.model.ConversionRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TemperatureValueValidator implements ConstraintValidator<ValidTemperatureValue, ConversionRequest> {
    @Override
    public boolean isValid(ConversionRequest request, ConstraintValidatorContext context) {
        if (request.getCategory() == Category.TEMPERATURE) {
            // Allow any value (including negatives) for temperature
            return true;
        }
        // For other categories, enforce positive or zero
        return request.getValue() >= 0;
    }
}
