package com.example.demo.controllers.DTOS;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record NewRentDTO(
        @Min(value = 0, message = "The rent of a property must be at-least 0.")
        @NotNull(message = "The rent of a property must be provided and at-least 0.")
        // Changed population from int to Integer so that it can return Null so that the above NotNUll message can be thrown
        Integer newRent) {
}
