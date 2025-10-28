package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.user.UserCreateDTO;
import com.polytechnique.fleetman.dto.user.UserDTO;
import com.polytechnique.fleetman.dto.user.UserPasswordUpdateDTO;
import com.polytechnique.fleetman.dto.user.UserUpdateDTO;
import com.polytechnique.fleetman.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Utilisateurs", description = "API de gestion des utilisateurs du système")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(
            summary = "Créer un nouvel utilisateur",
            description = """
            Crée un nouvel utilisateur dans le système.
            🔐 **Sécurité** : Le mot de passe est haché avant stockage.
            ⚠️ **Validation** :
            - Email doit être unique
            - Mot de passe minimum 8 caractères
            - Tous les champs sont obligatoires
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Utilisateur créé avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides - validation échouée",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Un utilisateur avec cet email existe déjà",
                    content = @Content
            )
    })
    public ResponseEntity<UserDTO> createUser(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Informations du nouvel utilisateur",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserCreateDTO.class))
            )
            UserCreateDTO userCreateDTO) {
        UserDTO createdUser = userService.createUser(userCreateDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    @Operation(
            summary = "Récupérer un utilisateur par son ID",
            description = "Retourne les informations détaillées d'un utilisateur spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Utilisateur trouvé",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utilisateur non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(
                    description = "ID de l'utilisateur",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId) {
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    @Operation(
            summary = "Récupérer un utilisateur par son email",
            description = "Recherche un utilisateur par son adresse email"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Utilisateur trouvé",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aucun utilisateur trouvé avec cet email",
                    content = @Content
            )
    })
    public ResponseEntity<UserDTO> getUserByEmail(
            @Parameter(
                    description = "Adresse email de l'utilisateur",
                    required = true,
                    example = "admin@example.com"
            )
            @PathVariable String email) {
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @Operation(
            summary = "Lister tous les utilisateurs",
            description = "Retourne la liste complète de tous les utilisateurs enregistrés"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Liste des utilisateurs récupérée avec succès",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))
            )
    )
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    @Operation(
            summary = "Mettre à jour un utilisateur",
            description = """
            Met à jour les informations d'un utilisateur existant.
            🔄 **Comportement** :
            - Seuls les champs fournis seront mis à jour
            - L'email doit rester unique si modifié
            - Les champs non fournis conservent leurs valeurs actuelles
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Utilisateur mis à jour avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides - validation échouée",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utilisateur non trouvé",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "L'email est déjà utilisé par un autre utilisateur",
                    content = @Content
            )
    })
    public ResponseEntity<UserDTO> updateUser(
            @Parameter(
                    description = "ID de l'utilisateur à modifier",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId,
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nouvelles informations de l'utilisateur",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserUpdateDTO.class))
            )
            UserUpdateDTO userUpdateDTO) {
        UserDTO updatedUser = userService.updateUser(userId, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{userId}")
    @Operation(
            summary = "Supprimer un utilisateur",
            description = "Supprime définitivement un utilisateur du système"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Utilisateur supprimé avec succès",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utilisateur non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(
                    description = "ID de l'utilisateur à supprimer",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}/password")
    @Operation(
            summary = "Modifier le mot de passe",
            description = """
            Permet à un utilisateur de modifier son propre mot de passe.
            🔐 **Sécurité** :
            - Vérification de l'ancien mot de passe
            - Confirmation du nouveau mot de passe
            - Hachage automatique du nouveau mot de passe
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Mot de passe modifié avec succès"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides ou mots de passe ne correspondent pas",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Ancien mot de passe incorrect",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utilisateur non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<Void> updateUserPassword(
            @Parameter(
                    description = "ID de l'utilisateur",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId,
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Informations de changement de mot de passe",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserPasswordUpdateDTO.class))
            )
            UserPasswordUpdateDTO passwordUpdateDTO) {
        userService.updateUserPassword(userId, passwordUpdateDTO);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{userId}/reset-password")
    @Operation(
            summary = "Réinitialiser le mot de passe (Admin)",
            description = """
            Réinitialise le mot de passe d'un utilisateur (fonctionnalité administrateur).
            ⚠️ **Attention** : Ne nécessite pas l'ancien mot de passe.
            🔒 **Accès** : Réservé aux administrateurs.
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Mot de passe réinitialisé avec succès"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Nouveau mot de passe vide ou invalide",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utilisateur non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<Void> resetUserPassword(
            @Parameter(
                    description = "ID de l'utilisateur",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId,
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nouveau mot de passe (texte brut)",
                    required = true,
                    content = @Content(schema = @Schema(implementation = String.class))
            )
            @NotBlank String newPassword) {
        userService.resetUserPassword(userId, newPassword);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/verify-password")
    @Operation(
            summary = "Vérifier un mot de passe",
            description = "Vérifie si le mot de passe fourni correspond à celui de l'utilisateur"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Résultat de la vérification",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Mot de passe non fourni",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utilisateur non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<Boolean> verifyPassword(
            @Parameter(
                    description = "ID de l'utilisateur",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId,
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Mot de passe à vérifier",
                    required = true,
                    content = @Content(schema = @Schema(implementation = String.class))
            )
            @NotBlank String password) {
        boolean isValid = userService.verifyPassword(userId, password);
        return ResponseEntity.ok(isValid);
    }
}