package com.itic.userservice.dtos.stat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatRequestDto {
    @Schema(example = "1300")
    @NotNull(message = "Le total de point ne peut pas être vide")
    private Long totalPoints;

    @Schema(example = "1")
    @NotNull(message = "Le nombre de challenges complétés ne peut pas être vide")
    private Integer totalChallengesCompleted;

    @Schema(example = "1000")
    @NotNull(message = "La distance parcourue ne peut pas être vide")
    private Long totalDistance;

    @Schema(example = "2000")
    private Long totalCalories;
}
