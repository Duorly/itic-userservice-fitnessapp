package com.itic.userservice.controllers;

import com.itic.userservice.dtos.user.UserRequestDto;
import com.itic.userservice.dtos.user.UserResponseDto;
import com.itic.userservice.exceptions.ApiException;
import com.itic.userservice.exceptions.ErrorCode;
import com.itic.userservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "Users", description = "Gestion des utilisateurs")
public class UserController {

    private final UserService userService;

    // ----------------------------------------------------
    // POST /users
    // ----------------------------------------------------
    @Operation(
            summary = "Créer un nouvel utilisateur",
            description = "Crée un utilisateur dans la base de données à partir du DTO fourni."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Utilisateur créé avec succès",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Erreur de validation",
                    content = @Content)
    })
    @PostMapping
    public UserResponseDto create(
            @Valid @RequestBody UserRequestDto dto
    ) {
        return userService.createUser(dto);
    }

    // ----------------------------------------------------
    // GET /users
    // ----------------------------------------------------
    @Operation(
            summary = "Lister tous les utilisateurs",
            description = "Retourne la liste complète des utilisateurs enregistrés."
    )
    @ApiResponse(responseCode = "200",
            description = "Liste récupérée avec succès",
            content = @Content(schema = @Schema(implementation = UserResponseDto.class)))
    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAllUsers();
    }

    // ----------------------------------------------------
    // GET /users/{id}
    // ----------------------------------------------------
    @Operation(
            summary = "Récupérer un utilisateur par son ID",
            description = "Retourne les informations complètes d'un utilisateur existant."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Utilisateur trouvé",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404",
                    description = "Utilisateur introuvable",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public UserResponseDto getById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // PUT /api/users/{id}
    @Operation(summary = "Met à jour un utilisateur existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto dto) {
        UserResponseDto updated = userService.updateUser(id, dto);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/users/{id}
    @Operation(summary = "Supprime un utilisateur existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
