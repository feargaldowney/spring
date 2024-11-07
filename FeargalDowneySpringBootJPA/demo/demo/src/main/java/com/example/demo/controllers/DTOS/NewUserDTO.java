package com.example.demo.controllers.DTOS;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewUserDTO(
        @NotBlank(message = "Email cannot be blank")
        String email,
        @NotBlank(message = "Password cannot be blank")
        String password,
        @NotBlank(message = "Role cannot be blank")
        String role,
        boolean locked,
        @NotNull(message = "Phone number cannot be null")
        int phone,
        @NotBlank(message = "PPSN cannot be blank")
        String PPSN) {
}
