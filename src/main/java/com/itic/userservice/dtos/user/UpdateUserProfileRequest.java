package com.itic.userservice.dtos.user;

import com.itic.userservice.enums.Gender;
import com.itic.userservice.enums.FitnessLevel;
import com.itic.userservice.enums.FitnessGoal;
import com.itic.userservice.enums.IntensityLevel;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileRequest {
    
    @Positive(message = "Le poids doit être positif")
    @Max(value = 500, message = "Le poids ne peut pas dépasser 500 kg")
    private Double weight;
    
    @Positive(message = "La taille doit être positive")
    @Min(value = 50, message = "La taille doit être d'au moins 50 cm")
    @Max(value = 300, message = "La taille ne peut pas dépasser 300 cm")
    private Integer height;
    
    @Past(message = "La date de naissance doit être dans le passé")
    private LocalDate dateOfBirth;
    
    private Gender gender;
    private FitnessLevel fitnessLevel;
    private FitnessGoal primaryGoal;
    
    @Positive(message = "Le poids cible doit être positif")
    @Max(value = 500, message = "Le poids cible ne peut pas dépasser 500 kg")
    private Double targetWeight;
    
    @Min(value = 1, message = "L'objectif doit être d'au moins 1 session par semaine")
    @Max(value = 14, message = "L'objectif ne peut pas dépasser 14 sessions par semaine")
    private Integer weeklyActivityGoal;
    
    private List<String> preferredActivities;
    
    @Positive(message = "La durée doit être positive")
    @Max(value = 480, message = "La durée ne peut pas dépasser 480 minutes")
    private Integer preferredWorkoutDuration;
    
    private IntensityLevel preferredIntensity;
    
    @Size(max = 1000, message = "Les conditions médicales ne peuvent pas dépasser 1000 caractères")
    private String medicalConditions;
    
    @Size(max = 1000, message = "Les blessures ne peuvent pas dépasser 1000 caractères")
    private String injuries;
    
    private List<String> preferredWorkoutTimes;
}