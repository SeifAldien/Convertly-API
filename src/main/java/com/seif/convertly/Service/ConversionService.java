package com.seif.convertly.Service;

import com.seif.convertly.enums.Category;
import com.seif.convertly.model.ConversionRequest;
import com.seif.convertly.model.ConversionResponse;

import java.util.List;

public interface ConversionService {
    ConversionResponse convert(ConversionRequest request);
    List<String> getSupportedUnits(Category category);
}
