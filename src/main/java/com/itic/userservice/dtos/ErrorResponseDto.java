package com.itic.userservice.dtos;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String description,
        String path
) {}
