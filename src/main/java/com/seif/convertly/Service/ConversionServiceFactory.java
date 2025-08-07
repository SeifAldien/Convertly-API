package com.seif.convertly.Service;

import com.seif.convertly.enums.Category;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConversionServiceFactory {
    private final Map<Category, ConversionService> services;

    public ConversionServiceFactory(List<ConversionService> serviceList) {
        services = new HashMap<>();
        services.put(Category.TEMPERATURE, getServiceOfType(serviceList, TemperatureService.class));
        services.put(Category.LENGTH, getServiceOfType(serviceList, LengthService.class));
        services.put(Category.WEIGHT, getServiceOfType(serviceList, WeightService.class));
        services.put(Category.TIME, getServiceOfType(serviceList, TimeService.class));
    }

    private ConversionService getServiceOfType(List<ConversionService> services, Class<?> type) {
        return services.stream()
                .filter(type::isInstance)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Service not found: " + type));
    }

    public ConversionService getService(Category category) {
        return services.get(category);
    }
}
