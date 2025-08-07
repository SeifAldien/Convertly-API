package com.seif.convertly.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Category {
        TEMPERATURE, LENGTH, WEIGHT, TIME;

        @JsonCreator
        public static Category fromString(String value) {
            try {
                return Category.valueOf(value.toUpperCase());
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid category: " + value);
            }
        }
    }
