package com.itic.userservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.itic.userservice.dtos.user.UpdateUserProfileRequest;
import com.itic.userservice.dtos.user.UserProfileDTO;
import com.itic.userservice.services.UserProfileService;

@RestController
@RequestMapping("/api/users/{userId}/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    /**
     * Récupérer le profil d'un utilisateur
     */
    @GetMapping
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable Long userId) {
        UserProfileDTO profile = userProfileService.getUserProfile(userId);
        return ResponseEntity.ok(profile);
    }

    /**
     * Créer le profil d'un utilisateur
     */
    @PostMapping
    public ResponseEntity<UserProfileDTO> createUserProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserProfileRequest request) {
        UserProfileDTO profile = userProfileService.createUserProfile(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(profile);
    }

    /**
     * Mettre à jour le profil d'un utilisateur
     */
    @PutMapping
    public ResponseEntity<UserProfileDTO> updateUserProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserProfileRequest request) {
        UserProfileDTO profile = userProfileService.updateUserProfile(userId, request);
        return ResponseEntity.ok(profile);
    }

    /**
     * Mettre à jour partiellement le profil (PATCH)
     */
    @PatchMapping
    public ResponseEntity<UserProfileDTO> patchUserProfile(
            @PathVariable Long userId,
            @RequestBody UpdateUserProfileRequest request) {
        UserProfileDTO profile = userProfileService.updateUserProfile(userId, request);
        return ResponseEntity.ok(profile);
    }

    /**
     * Supprimer le profil d'un utilisateur
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long userId) {
        userProfileService.deleteUserProfile(userId);
        return ResponseEntity.noContent().build();
    }
}