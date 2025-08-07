package com.seif.convertly.Service;

import com.seif.convertly.enums.Category;
import com.seif.convertly.enums.TemperatureUnit;
import com.seif.convertly.exception.InvalidUnitException;
import com.seif.convertly.model.ConversionRequest;
import com.seif.convertly.model.ConversionResponse;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemperatureService implements ConversionService {
    @Override
    public ConversionResponse convert(ConversionRequest request) {
        try {
            TemperatureUnit fromUnit = TemperatureUnit.valueOf(request.getFromUnit().toUpperCase());
            TemperatureUnit toUnit = TemperatureUnit.valueOf(request.getToUnit().toUpperCase());
            return convertTemperature(fromUnit, toUnit, request.getValue());
        } catch (IllegalArgumentException e) {
            throw new InvalidUnitException("Invalid temperature unit");
        }

    }

    private ConversionResponse convertTemperature(TemperatureUnit fromUnit, TemperatureUnit toUnit, double value){
        double result;
        String formula = "";
        double inCelsius;

        // Convert to Celsius first
        switch (fromUnit) {
            case CELSIUS:
                inCelsius = value;
                break;
            case FAHRENHEIT:
                inCelsius = (value - 32) * 5.0/9.0;
                formula += String.format("(%.2f°F - 32) × 5/9 = %.2f°C\n", value, inCelsius);
                break;
            case KELVIN:
                inCelsius = value - 273.15;
                formula += String.format("%.2fK - 273.15 = %.2f°C\n", value, inCelsius);
                break;
            default:
                throw new InvalidUnitException("Invalid source temperature unit");
        }

        // Convert from Celsius to target unit
        switch (toUnit) {
            case CELSIUS:
                result = inCelsius;
                formula += String.format("%.2f°C (no conversion needed)", inCelsius);
                break;
            case FAHRENHEIT:
                result = (inCelsius * 9/5) + 32;
                formula += String.format("(%.2f°C × 9/5) + 32 = %.2f°F", inCelsius, result);
                break;
            case KELVIN:
                result = inCelsius + 273.15;
                formula += String.format("%.2f°C + 273.15 = %.2fK", inCelsius, result);
                break;
            default:
                throw new InvalidUnitException("Invalid target temperature unit");
        }

        return new ConversionResponse(result, formula.trim(), "success");

    }

   @Override
    public List<String> getSupportedUnits(Category category) {
       return Arrays.stream(TemperatureUnit.values())
               .map(Enum::name)
               .collect(Collectors.toList());
   }
}
