package com.example.demo.controllers.handlers;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ApiErrorResponse(String message,
                               @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy hh:mm")
                               LocalDateTime localDateTime) {
}
