package com.polytechnique.fleetman.dto.user;


import io.swagger.v3.oas.annotations.media.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO pour la création d'un utilisateur")
public class UserCreateDTO {

    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Schema(description = "Nom complet de l'utilisateur", example = "Jean Dupont", requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String userName;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Schema(description = "Mot de passe (minimum 8 caractères)", example = "MonMot2Passe!", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 8)
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String userPassword;

    @NotBlank(message = "L'email est obligatoire")
    @Schema(description = "Adresse email", example = "jean.dupont@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @Email(message = "Format d'email invalide")
    @Size(max = 150, message = "L'email ne doit pas dépasser 150 caractères")
    private String userEmail;

    @Schema(description = "Numéro de téléphone", example = "+33123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Size(max = 20, message = "Le numéro ne doit pas dépasser 20 caractères")
    private String userPhoneNumber;

}
