package com.itic.userservice.services;


import com.itic.userservice.dtos.stat.StatRequestDto;
import com.itic.userservice.dtos.stat.StatResponseDto;
import com.itic.userservice.entities.UserStat;
import com.itic.userservice.exceptions.ApiException;
import com.itic.userservice.exceptions.ErrorCode;
import com.itic.userservice.repositories.UserRepository;
import com.itic.userservice.repositories.UserStatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserStatService {

    private final UserStatRepository userStatRepository;

    @Transactional
    public StatResponseDto getUserStat(Long userId){
        UserStat userStat = userStatRepository.findById(userId).orElseThrow(
                () -> new ApiException(
                        ErrorCode.USER_NOT_FOUND,
                        String.format("UserStat with id %d not found", userId)
                )
        );

        return mapToResponse(userStat);
    }

    public StatResponseDto updateUserStat(Long userId, StatRequestDto dto) {
        UserStat userStat = userStatRepository.findById(userId).orElseThrow(
                () -> new ApiException(
                        ErrorCode.USER_NOT_FOUND,
                        String.format("UserStat with id %d not found", userId)
                )
        );

        // Mise à jour des champs (uniquement ceux fournis)
        if (dto.getTotalPoints() != null) userStat.setTotalPoints(dto.getTotalPoints());
        if (dto.getTotalChallengesCompleted() != null) userStat.setTotalChallengesCompleted(dto.getTotalChallengesCompleted());
        if (dto.getTotalDistance() != null) userStat.setTotalDistance(dto.getTotalDistance());
        if (dto.getTotalCalories() != null) userStat.setTotalCalories(dto.getTotalCalories());

        UserStat updated = userStatRepository.save(userStat);
        return mapToResponse(updated);
    }

    private StatResponseDto mapToResponse(UserStat userStat) {
        StatResponseDto dto = new StatResponseDto();
        dto.setUserId(userStat.getId());
        dto.setLevel(userStat.getLevel());
        dto.setTotalCalories(userStat.getTotalPoints());
        dto.setTotalDistance(userStat.getTotalDistance());
        dto.setTotalChallengesCompleted(userStat.getTotalChallengesCompleted());
        dto.setTotalPoints(userStat.getTotalPoints());
        dto.setUpdatedAt(userStat.getUpdatedAt());
        return dto;
    }

}
