package com.itic.userservice.dtos.user;

import com.itic.userservice.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @Schema(example = "nebel123")
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 30, message = "Le nom d'utilisateur doit contenir entre 3 et 30 caractères")
    private String username;

    @Schema(example = "StrongPass!2024")
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String password;

    @Schema(example = "nebel@example.com")
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    private String email;

    @Schema(example = "Nebel")
    @NotBlank(message = "Le prénom est obligatoire")
    private String firstName;

    @Schema(example = "Doe")
    @NotBlank(message = "Le nom de famille est obligatoire")
    private String lastName;

    @Schema(example = "1992-08-15")
    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateOfBirth;

    @Schema(example = "MALE")
    @NotNull(message = "Le genre est obligatoire")
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
