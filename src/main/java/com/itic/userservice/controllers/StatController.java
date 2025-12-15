package com.itic.userservice.controllers;

import com.itic.userservice.dtos.stat.StatRequestDto;
import com.itic.userservice.dtos.stat.StatResponseDto;
import com.itic.userservice.dtos.user.UserResponseDto;
import com.itic.userservice.services.UserStatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{id}/stats")
@Tag(name = "Stats", description = "Gestion des utilisateurs")
public class StatController {

    private final UserStatService userStatService;

    // ----------------------------------------------------
    @Operation(
            summary = "Récupérer les statistiques d'un utilisateur",
            description = "Récupère toutes les statistiques d'un utilisateur (points, niveau, défis complétés, etc.)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Statistique de l'utilisateur récupéré avec succès",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Erreur de validation",
                    content = @Content)
    })
    @GetMapping
    public StatResponseDto getStats(
            @PathVariable Long id
    ) {
       return userStatService.getUserStat(id);
    }

    // ----------------------------------------------------
    @Operation(
            summary = "Mettre à jour les statistiques d'un utilisateur",
            description = "Met à jour les statistiques"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Statistique de l'utilisateur mise à jour avec succès",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Erreur de validation",
                    content = @Content)
    })
    @PutMapping
    public StatResponseDto updateStats(
            @PathVariable Long id,
            @Valid @RequestBody StatRequestDto dto
    ) {
        return userStatService.updateUserStat(id, dto);
    }
}
