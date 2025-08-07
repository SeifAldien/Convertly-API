package com.seif.convertly.Service;

import com.seif.convertly.enums.Category;
import com.seif.convertly.enums.LengthUnit;
import com.seif.convertly.enums.TemperatureUnit;
import com.seif.convertly.exception.InvalidUnitException;
import com.seif.convertly.model.ConversionRequest;
import com.seif.convertly.model.ConversionResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LengthService implements ConversionService {

    @Override
    public ConversionResponse convert(ConversionRequest request) {
        try {
            LengthUnit fromUnit = LengthUnit.valueOf(request.getFromUnit().toUpperCase());
            LengthUnit toUnit = LengthUnit.valueOf(request.getToUnit().toUpperCase());
            return convertLength(fromUnit, toUnit, request.getValue());
        } catch (IllegalArgumentException e) {
            throw new InvalidUnitException("Invalid length unit");
        }

    }

    public ConversionResponse convertLength(LengthUnit fromUnit, LengthUnit toUnit, double value) {
        // Convert to meters first (base unit)
        double inMeters;
        String formula = "";

        switch (fromUnit) {
            case METER:
                inMeters = value;
                break;
            case KILOMETER:
                inMeters = value * 1000;
                formula += String.format("%.2f km × 1000 = %.2f m\n", value, inMeters);
                break;
            case MILE:
                inMeters = value * 1609.344;
                formula += String.format("%.2f mi × 1609.344 = %.2f m\n", value, inMeters);
                break;
            case INCH:
                inMeters = value * 0.0254;
                formula += String.format("%.2f in × 0.0254 = %.2f m\n", value, inMeters);
                break;
            case FOOT:
                inMeters = value * 0.3048;
                formula += String.format("%.2f ft × 0.3048 = %.2f m\n", value, inMeters);
                break;
            default:
                throw new InvalidUnitException("Invalid source length unit");
        }

        // Convert from meters to target unit
        double result;
        switch (toUnit) {
            case METER:
                result = inMeters;
                formula += String.format("%.2f m (no conversion needed)", inMeters);
                break;
            case KILOMETER:
                result = inMeters / 1000;
                formula += String.format("%.2f m ÷ 1000 = %.2f km", inMeters, result);
                break;
            case MILE:
                result = inMeters / 1609.344;
                formula += String.format("%.2f m ÷ 1609.344 = %.2f mi", inMeters, result);
                break;
            case INCH:
                result = inMeters / 0.0254;
                formula += String.format("%.2f m ÷ 0.0254 = %.2f in", inMeters, result);
                break;
            case FOOT:
                result = inMeters / 0.3048;
                formula += String.format("%.2f m ÷ 0.3048 = %.2f ft", inMeters, result);
                break;
            default:
                throw new InvalidUnitException("Invalid target length unit");
        }

        return new ConversionResponse(result, formula.trim(), "success");
    }

    @Override
    public List<String> getSupportedUnits(Category category) {
        return Arrays.stream(LengthUnit.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

}
