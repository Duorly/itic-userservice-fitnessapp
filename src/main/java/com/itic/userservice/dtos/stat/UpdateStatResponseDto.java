package com.itic.userservice.dtos.stat;

import lombok.Data;

@Data
public class UpdateStatResponseDto {
    private boolean success;
    private Long newTotal;
    private Integer newLevel;
    private boolean levelUp;
}
