package com.itic.userservice.dtos.stat;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StatResponseDto {
    private Long userId;
    private Long totalPoints;
    private Integer level;
    private Integer totalChallengesCompleted;
    private Long totalDistance;
    private Long totalCalories;
    private LocalDateTime updatedAt;
}
