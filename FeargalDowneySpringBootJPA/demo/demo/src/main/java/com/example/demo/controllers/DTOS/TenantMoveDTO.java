package com.example.demo.controllers.DTOS;

import jakarta.validation.constraints.NotBlank;

public record TenantMoveDTO(
        @NotBlank(message = "Email cannot be blank")
        int tenantEmail,
        @NotBlank(message = "Eircode cannot be blank")
        int newEircode) {
}
