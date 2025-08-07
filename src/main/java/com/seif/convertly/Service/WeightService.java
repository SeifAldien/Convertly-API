package com.seif.convertly.Service;

import com.seif.convertly.enums.Category;
import com.seif.convertly.enums.TemperatureUnit;
import com.seif.convertly.enums.WeightUnit;
import com.seif.convertly.exception.InvalidUnitException;
import com.seif.convertly.model.ConversionRequest;
import com.seif.convertly.model.ConversionResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeightService implements ConversionService{

    @Override
    public ConversionResponse convert(ConversionRequest request) {
        try {
            WeightUnit fromUnit = WeightUnit.valueOf(request.getFromUnit().toUpperCase());
            WeightUnit toUnit = WeightUnit.valueOf(request.getToUnit().toUpperCase());
            return convertWeight(fromUnit, toUnit, request.getValue());
        } catch (IllegalArgumentException e) {
            throw new InvalidUnitException("Invalid weight unit");
        }
    }

    public ConversionResponse convertWeight(WeightUnit fromUnit, WeightUnit toUnit, double value) {
        // Convert to kilograms first (base unit)
        double inKilograms;
        String formula = "";

        switch (fromUnit) {
            case KILOGRAM:
                inKilograms = value;
                break;
            case GRAM:
                inKilograms = value / 1000;
                formula += String.format("%.2f g ÷ 1000 = %.2f kg\n", value, inKilograms);
                break;
            case POUND:
                inKilograms = value * 0.453592;
                formula += String.format("%.2f lb × 0.453592 = %.2f kg\n", value, inKilograms);
                break;
            case OUNCE:
                inKilograms = value * 0.0283495;
                formula += String.format("%.2f oz × 0.0283495 = %.2f kg\n", value, inKilograms);
                break;
            default:
                throw new InvalidUnitException("Invalid source weight unit");
        }

        // Convert from kilograms to target unit
        double result;
        switch (toUnit) {
            case KILOGRAM:
                result = inKilograms;
                formula += String.format("%.2f kg (no conversion needed)", inKilograms);
                break;
            case GRAM:
                result = inKilograms * 1000;
                formula += String.format("%.2f kg × 1000 = %.2f g", inKilograms, result);
                break;
            case POUND:
                result = inKilograms / 0.453592;
                formula += String.format("%.2f kg ÷ 0.453592 = %.2f lb", inKilograms, result);
                break;
            case OUNCE:
                result = inKilograms / 0.0283495;
                formula += String.format("%.2f kg ÷ 0.0283495 = %.2f oz", inKilograms, result);
                break;
            default:
                throw new InvalidUnitException("Invalid target weight unit");
        }

        return new ConversionResponse(result, formula.trim(), "success");
    }

    @Override
    public List<String> getSupportedUnits(Category category) {
        return Arrays.stream(WeightUnit.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
