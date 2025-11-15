package com.itic.userservice.exceptions;

import com.itic.userservice.dtos.ErrorResponseDto;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponseDto> handleApiException(ApiException ex, HttpServletRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
                LocalDateTime.now(),
                ex.getErrorCode().getStatus().value(),
                ex.getErrorCode().getStatus().getReasonPhrase(),
                ex.getErrorCode().getMessage(),
                ex.getDescription(),  // description dynamique
                request.getRequestURI()
        );
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(error);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class, ConstraintViolationException.class })
    public ResponseEntity<ErrorResponseDto> handleConstraintViolation(Exception ex, HttpServletRequest request) {

        String description = "Violation d'une contrainte en base de données";
        String message = "Données invalides ou déjà existantes";

        // Si tu veux, tu peux extraire le champ spécifique depuis le message SQL
        if (ex.getCause() instanceof ConstraintViolationException cve) {
            String constraint = cve.getConstraintName();
            message = "Conflit : la contrainte " + constraint + " a été violée";
            description = cve.getMessage();
        }

        ErrorResponseDto error = new ErrorResponseDto(
                LocalDateTime.now(),
                400, // Bad Request ou 409 Conflict selon le cas
                "Constraint Violation",
                message,
                description,
                request.getRequestURI()
        );
        return ResponseEntity.status(409).body(error); // 409 Conflict est plus approprié ici
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex, HttpServletRequest request) {
        ErrorResponseDto error = new ErrorResponseDto(
                LocalDateTime.now(),
                500,
                "Internal Server Error",
                ex.getMessage(),
                "Erreur inattendue côté serveur",
                request.getRequestURI()
        );
        return ResponseEntity.status(500).body(error);
    }
}

