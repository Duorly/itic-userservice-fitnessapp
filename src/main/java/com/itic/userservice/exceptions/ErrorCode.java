package com.itic.userservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND("User not found", HttpStatus.NOT_FOUND),
    INVALID_REQUEST("Invalid request", HttpStatus.BAD_REQUEST),
    PROFILE_NOT_FOUND("Profile not found", HttpStatus.NOT_FOUND),
    PROFILE_ALREADY_EXISTS("Profile already exists", HttpStatus.CONFLICT),
    INVALID_PROFILE_DATA("Invalid profile data", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}

