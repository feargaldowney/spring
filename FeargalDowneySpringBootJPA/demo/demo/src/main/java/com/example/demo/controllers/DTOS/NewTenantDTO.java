package com.example.demo.controllers.DTOS;

import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewTenantDTO(
        @NotEmpty(message = "Tenant's name cannot be empty")
        @NotBlank(message = "Tenant's name cannot be blank")
        @NotNull(message = "Tenant's name cannot be null")
        String newTenantName,
        @NotEmpty(message = "Tenant's number cannot be empty")
        @NotBlank(message = "Tenant's number cannot be blank")
        @NotNull(message = "Tenant's number cannot be null")
        int newTenantPhone,
        @Min(value = 0, message = "Invalid property EirCode")
        @NotNull(message = "The EirCode of a property must be provided.")
        // Changed population from int to Integer so that it can return Null so that the above NotNUll message can be thrown
        Integer newPropertyEircode) {
}
