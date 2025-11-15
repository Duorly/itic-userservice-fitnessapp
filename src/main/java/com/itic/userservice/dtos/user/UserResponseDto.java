package com.itic.userservice.dtos.user;

import com.itic.userservice.enums.Gender;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
