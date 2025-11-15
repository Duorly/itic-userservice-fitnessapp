package com.itic.userservice.services;

import com.itic.userservice.dtos.user.UserRequestDto;
import com.itic.userservice.dtos.user.UserResponseDto;
import com.itic.userservice.entities.User;
import com.itic.userservice.entities.UserStat;
import com.itic.userservice.exceptions.ApiException;
import com.itic.userservice.exceptions.ErrorCode;
import com.itic.userservice.repositories.UserRepository;
import com.itic.userservice.repositories.UserStatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserStatRepository userStatRepository;

    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .build();

        User saved = userRepository.save(user);

        UserStat stat = UserStat.builder()
                .user(saved)
                .totalPoints(0L)
                .level(1)
                .nextLevelPoints(100L)
                .totalChallengesCompleted(0)
                .totalDistance(0L)
                .totalCalories(0L)
                .updatedAt(LocalDateTime.now())
                .build();

        userStatRepository.save(stat);

        return mapToResponse(saved);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new ApiException(
                        ErrorCode.USER_NOT_FOUND,
                        String.format("User with id %d not found", id)
                ));
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(
                        ErrorCode.USER_NOT_FOUND,
                        String.format("User with id %d not found", id)
                ));

        // Mise à jour des champs (uniquement ceux fournis)
        if (dto.getUsername() != null) user.setUsername(dto.getUsername());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPassword() != null) user.setPassword(dto.getPassword());
        if (dto.getFirstName() != null) user.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) user.setLastName(dto.getLastName());
        if (dto.getDateOfBirth() != null) user.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getGender() != null) user.setGender(dto.getGender());

        User updated = userRepository.save(user);
        return mapToResponse(updated);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiException(
                        ErrorCode.USER_NOT_FOUND,
                        String.format("User with id %d not found", id)
                ));

        // Supprimer d'abord le UserStat associé
        userStatRepository.findById(user.getId())
                .ifPresent(userStatRepository::delete);

        // Supprimer l'utilisateur
        userRepository.delete(user);
    }

    private UserResponseDto mapToResponse(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setGender(user.getGender());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}
