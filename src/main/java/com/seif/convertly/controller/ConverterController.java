package com.seif.convertly.controller;


import com.seif.convertly.Service.ConversionServiceFactory;
import com.seif.convertly.enums.Category;
import com.seif.convertly.exception.InvalidUnitException;
import com.seif.convertly.model.ConversionRequest;
import com.seif.convertly.model.ConversionResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Validated
public class ConverterController {
    private final ConversionServiceFactory serviceFactory;

    public ConverterController(ConversionServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @PostMapping("/convert")
    public ResponseEntity<ConversionResponse> convert(
            @Valid @RequestBody ConversionRequest request) {
        ConversionResponse response = serviceFactory.getService(request.getCategory())
                .convert(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(
                Stream.of(Category.values())
                        .map(Enum::name)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/units")
    public ResponseEntity<List<String>> getUnits(
            @RequestParam String category) {  // Changed from Category to String
        try {
            Category categoryEnum = Category.valueOf(category.toUpperCase());
            return ResponseEntity.ok(
                    serviceFactory.getService(categoryEnum)
                            .getSupportedUnits(categoryEnum)
            );
        } catch (IllegalArgumentException e) {
            throw new InvalidUnitException("Invalid category: " + category);
        }
    }

    @GetMapping("/sample-payload")
    public ResponseEntity<ConversionRequest> getSamplePayload() {
        ConversionRequest sample = new ConversionRequest();
        sample.setCategory(Category.TEMPERATURE);
        sample.setFromUnit("CELSIUS");
        sample.setToUnit("FAHRENHEIT");
        sample.setValue(25.0);
        return ResponseEntity.ok(sample);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck() {
        return ResponseEntity.ok(
                Collections.singletonMap("status", "Unit Converter API is up and running")
        );
    }
}
