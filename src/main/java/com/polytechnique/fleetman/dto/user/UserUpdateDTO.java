package com.polytechnique.fleetman.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO pour la mise à jour d'un utilisateur")
public class UserUpdateDTO {

    @Schema(description = "Nom complet de l'utilisateur", example = "Jean Dupont", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String userName;

    @Schema(description = "Adresse email", example = "jean.dupont@example.com", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Email(message = "Format d'email invalide")
    @Size(max = 150, message = "L'email ne doit pas dépasser 150 caractères")
    private String userEmail;

    @Schema(description = "Numéro de téléphone", example = "+33123456789", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 20, message = "Le numéro ne doit pas dépasser 20 caractères")
    private String userPhoneNumber;
}
