package com.itic.userservice.services;

import com.itic.userservice.dtos.user.UserRequestDto;
import com.itic.userservice.dtos.user.UserResponseDto;
import com.itic.userservice.entities.User;
import com.itic.userservice.exceptions.ApiException;
import com.itic.userservice.exceptions.ErrorCode;
import com.itic.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
