package com.seif.convertly.model;

import com.seif.convertly.enums.Category;
import com.seif.convertly.validator.ValidTemperatureValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@ValidTemperatureValue
public class ConversionRequest {
    @NotNull(message = "Category is required")
    private Category category;

    @NotBlank(message = "from unit cannot be empty")
    private String fromUnit;

    @NotBlank(message = "to unit cannot be blank")
    private String toUnit;

    @NotNull(message = "Value is required")
    private double value;
}
