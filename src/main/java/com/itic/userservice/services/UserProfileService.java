package com.itic.userservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itic.userservice.dtos.user.UpdateUserProfileRequest;
import com.itic.userservice.dtos.user.UserProfileDTO;
import com.itic.userservice.entities.User;
import com.itic.userservice.entities.UserProfile;
import com.itic.userservice.exceptions.ApiException;
import com.itic.userservice.exceptions.ErrorCode;
import com.itic.userservice.repositories.UserProfileRepository;
import com.itic.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Transactional(readOnly = true)
    public UserProfileDTO getUserProfile(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException(
                        ErrorCode.PROFILE_NOT_FOUND, 
                        "Profil non trouvé pour l'utilisateur avec l'ID: " + userId
                ));
        return mapToDTO(profile);
    }

    @Transactional
    public UserProfileDTO createUserProfile(Long userId, UpdateUserProfileRequest request) {
        if (userProfileRepository.existsByUserId(userId)) {
            throw new ApiException(
                    ErrorCode.PROFILE_ALREADY_EXISTS,
                    "Le profil existe déjà pour l'utilisateur avec l'ID: " + userId
            );
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(
                        ErrorCode.USER_NOT_FOUND,
                        "Utilisateur non trouvé avec l'ID: " + userId
                ));

        UserProfile profile = UserProfile.builder()
                .user(user)
                .weight(request.getWeight())
                .height(request.getHeight())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .fitnessLevel(request.getFitnessLevel())
                .primaryGoal(request.getPrimaryGoal())
                .targetWeight(request.getTargetWeight())
                .weeklyActivityGoal(request.getWeeklyActivityGoal())
                .preferredActivities(listToJson(request.getPreferredActivities()))
                .preferredWorkoutDuration(request.getPreferredWorkoutDuration())
                .preferredIntensity(request.getPreferredIntensity())
                .medicalConditions(request.getMedicalConditions())
                .injuries(request.getInjuries())
                .preferredWorkoutTimes(listToJson(request.getPreferredWorkoutTimes()))
                .build();

        UserProfile savedProfile = userProfileRepository.save(profile);
        log.info("Profil créé pour l'utilisateur {}", userId);
        return mapToDTO(savedProfile);
    }

    @Transactional
    public UserProfileDTO updateUserProfile(Long userId, UpdateUserProfileRequest request) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException(
                        ErrorCode.PROFILE_NOT_FOUND,
                        "Profil non trouvé pour l'utilisateur avec l'ID: " + userId
                ));

        // Mise à jour des champs si présents
        if (request.getWeight() != null) profile.setWeight(request.getWeight());
        if (request.getHeight() != null) profile.setHeight(request.getHeight());
        if (request.getDateOfBirth() != null) profile.setDateOfBirth(request.getDateOfBirth());
        if (request.getGender() != null) profile.setGender(request.getGender());
        if (request.getFitnessLevel() != null) profile.setFitnessLevel(request.getFitnessLevel());
        if (request.getPrimaryGoal() != null) profile.setPrimaryGoal(request.getPrimaryGoal());
        if (request.getTargetWeight() != null) profile.setTargetWeight(request.getTargetWeight());
        if (request.getWeeklyActivityGoal() != null) profile.setWeeklyActivityGoal(request.getWeeklyActivityGoal());
        if (request.getPreferredActivities() != null) profile.setPreferredActivities(listToJson(request.getPreferredActivities()));
        if (request.getPreferredWorkoutDuration() != null) profile.setPreferredWorkoutDuration(request.getPreferredWorkoutDuration());
        if (request.getPreferredIntensity() != null) profile.setPreferredIntensity(request.getPreferredIntensity());
        if (request.getMedicalConditions() != null) profile.setMedicalConditions(request.getMedicalConditions());
        if (request.getInjuries() != null) profile.setInjuries(request.getInjuries());
        if (request.getPreferredWorkoutTimes() != null) profile.setPreferredWorkoutTimes(listToJson(request.getPreferredWorkoutTimes()));

        UserProfile updatedProfile = userProfileRepository.save(profile);
        log.info("Profil mis à jour pour l'utilisateur {}", userId);
        return mapToDTO(updatedProfile);
    }

    @Transactional
    public void deleteUserProfile(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException(
                        ErrorCode.PROFILE_NOT_FOUND,
                        "Profil non trouvé pour l'utilisateur avec l'ID: " + userId
                ));
        userProfileRepository.delete(profile);
        log.info("Profil supprimé pour l'utilisateur {}", userId);
    }

    // Méthodes utilitaires
    private UserProfileDTO mapToDTO(UserProfile profile) {
        return UserProfileDTO.builder()
                .userId(profile.getUser().getId())
                .weight(profile.getWeight())
                .height(profile.getHeight())
                .dateOfBirth(profile.getDateOfBirth())
                .gender(profile.getGender())
                .fitnessLevel(profile.getFitnessLevel())
                .primaryGoal(profile.getPrimaryGoal())
                .targetWeight(profile.getTargetWeight())
                .weeklyActivityGoal(profile.getWeeklyActivityGoal())
                .preferredActivities(jsonToList(profile.getPreferredActivities()))
                .preferredWorkoutDuration(profile.getPreferredWorkoutDuration())
                .preferredIntensity(profile.getPreferredIntensity())
                .medicalConditions(profile.getMedicalConditions())
                .injuries(profile.getInjuries())
                .preferredWorkoutTimes(jsonToList(profile.getPreferredWorkoutTimes()))
                .age(calculateAge(profile.getDateOfBirth()))
                .bmi(calculateBMI(profile.getWeight(), profile.getHeight()))
                .bmiCategory(getBMICategory(calculateBMI(profile.getWeight(), profile.getHeight())))
                .build();
    }

    private Integer calculateAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null) return null;
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    private Double calculateBMI(Double weight, Integer height) {
        if (weight == null || height == null || height == 0) return null;
        double heightInMeters = height / 100.0;
        return Math.round(weight / (heightInMeters * heightInMeters) * 10.0) / 10.0;
    }

    private String getBMICategory(Double bmi) {
        if (bmi == null) return null;
        if (bmi < 18.5) return "Insuffisance pondérale";
        if (bmi < 25) return "Poids normal";
        if (bmi < 30) return "Surpoids";
        return "Obésité";
    }

    private String listToJson(List<String> list) {
        if (list == null || list.isEmpty()) return null;
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            log.error("Erreur lors de la conversion liste -> JSON", e);
            return null;
        }
    }

    private List<String> jsonToList(String json) {
        if (json == null || json.isEmpty()) return new ArrayList<>();
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            log.error("Erreur lors de la conversion JSON -> liste", e);
            return new ArrayList<>();
        }
    }
}