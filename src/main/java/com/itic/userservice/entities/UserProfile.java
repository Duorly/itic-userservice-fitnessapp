package com.itic.userservice.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.itic.userservice.enums.FitnessGoal;
import com.itic.userservice.enums.FitnessLevel;
import com.itic.userservice.enums.Gender;
import com.itic.userservice.enums.IntensityLevel;

@Entity
@Table(name = "user_profiles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    // Informations physiques
    @Column(name = "weight")
    private Double weight; // en kg

    @Column(name = "height")
    private Integer height; // en cm

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    // Niveau sportif
    @Enumerated(EnumType.STRING)
    @Column(name = "fitness_level")
    private FitnessLevel fitnessLevel;

    // Objectifs
    @Enumerated(EnumType.STRING)
    @Column(name = "primary_goal")
    private FitnessGoal primaryGoal;

    @Column(name = "target_weight")
    private Double targetWeight;

    @Column(name = "weekly_activity_goal")
    private Integer weeklyActivityGoal; // nombre de sessions par semaine

    // Préférences sportives
    @Column(name = "preferred_activities", length = 500)
    private String preferredActivities; 

    @Column(name = "preferred_workout_duration")
    private Integer preferredWorkoutDuration; // en minutes

    @Enumerated(EnumType.STRING)
    @Column(name = "preferred_intensity")
    private IntensityLevel preferredIntensity;

    // Contraintes et disponibilités
    @Column(name = "medical_conditions", length = 1000)
    private String medicalConditions;

    @Column(name = "injuries", length = 1000)
    private String injuries;

    @Column(name = "preferred_workout_times", length = 500)
    private String preferredWorkoutTimes; 

    // Métadonnées
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}