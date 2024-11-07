package com.example.demo.controllers.DTOS;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewPropertyDTO(
        @NotEmpty(message = "Property's name cannot be empty")
        @NotBlank(message = "Property's name cannot be blank")
        @NotNull(message = "Property's name cannot be null")
        String propertyAddress,
        @Min(value = 0, message = "The population of a property must be at-least 0.")
        @NotNull(message = "The population of a property must be provided and at-least 0.")
        // Changed population from int to Integer so that it can return Null so that the above NotNUll message can be thrown
        Integer propertyCapacity,
        Integer propertyRent) {
}
