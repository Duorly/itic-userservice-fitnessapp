package com.itic.userservice.dtos.user;

import com.itic.userservice.enums.Gender;
import com.itic.userservice.enums.FitnessLevel;
import com.itic.userservice.enums.FitnessGoal;
import com.itic.userservice.enums.IntensityLevel;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private Long userId;
    
    // Informations physiques
    private Double weight;
    private Integer height;
    private LocalDate dateOfBirth;
    private Gender gender;
    
    // Niveau sportif
    private FitnessLevel fitnessLevel;
    
    // Objectifs
    private FitnessGoal primaryGoal;
    private Double targetWeight;
    private Integer weeklyActivityGoal;
    
    // Préférences
    private List<String> preferredActivities;
    private Integer preferredWorkoutDuration;
    private IntensityLevel preferredIntensity;
    
    // Contraintes
    private String medicalConditions;
    private String injuries;
    private List<String> preferredWorkoutTimes;
    
    // Calculs dérivés
    private Integer age;
    private Double bmi;
    private String bmiCategory;
}