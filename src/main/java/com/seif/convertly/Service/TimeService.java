package com.seif.convertly.Service;

import com.seif.convertly.enums.Category;
import com.seif.convertly.enums.TemperatureUnit;
import com.seif.convertly.enums.TimeUnit;
import com.seif.convertly.enums.WeightUnit;
import com.seif.convertly.exception.InvalidUnitException;
import com.seif.convertly.model.ConversionRequest;
import com.seif.convertly.model.ConversionResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeService implements ConversionService{

    @Override
    public ConversionResponse convert(ConversionRequest request) {
        try {
            TimeUnit fromUnit = TimeUnit.valueOf(request.getFromUnit().toUpperCase());
            TimeUnit toUnit = TimeUnit.valueOf(request.getToUnit().toUpperCase());
            return convertTime(fromUnit, toUnit, request.getValue());
        } catch (IllegalArgumentException e) {
            throw new InvalidUnitException("Invalid time unit");
        }
    }

    public ConversionResponse convertTime(TimeUnit fromUnit, TimeUnit toUnit, double value) {
        // Convert to seconds first (base unit)
        double inSeconds;
        String formula = "";

        switch (fromUnit) {
            case SECOND:
                inSeconds = value;
                break;
            case MINUTE:
                inSeconds = value * 60;
                formula += String.format("%.2f min × 60 = %.2f sec\n", value, inSeconds);
                break;
            case HOUR:
                inSeconds = value * 3600;
                formula += String.format("%.2f hr × 3600 = %.2f sec\n", value, inSeconds);
                break;
            case DAY:
                inSeconds = value * 86400;
                formula += String.format("%.2f day × 86400 = %.2f sec\n", value, inSeconds);
                break;
            default:
                throw new InvalidUnitException("Invalid source time unit");
        }

        // Convert from seconds to target unit
        double result;
        switch (toUnit) {
            case SECOND:
                result = inSeconds;
                formula += String.format("%.2f sec (no conversion needed)", inSeconds);
                break;
            case MINUTE:
                result = inSeconds / 60;
                formula += String.format("%.2f sec ÷ 60 = %.2f min", inSeconds, result);
                break;
            case HOUR:
                result = inSeconds / 3600;
                formula += String.format("%.2f sec ÷ 3600 = %.2f hr", inSeconds, result);
                break;
            case DAY:
                result = inSeconds / 86400;
                formula += String.format("%.2f sec ÷ 86400 = %.2f day", inSeconds, result);
                break;
            default:
                throw new InvalidUnitException("Invalid target time unit");
        }

        return new ConversionResponse(result, formula.trim(), "success");
    }

    @Override
    public List<String> getSupportedUnits(Category category) {
        return Arrays.stream(TimeUnit.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
