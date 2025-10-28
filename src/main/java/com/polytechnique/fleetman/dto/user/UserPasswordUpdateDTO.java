package com.polytechnique.fleetman.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO pour la modification du mot de passe")
public class UserPasswordUpdateDTO {

    @Schema(description = "Ancien mot de passe", example = "AncienMot2Passe!", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "L'ancien mot de passe est obligatoire")
    private String oldPassword;

    @Schema(description = "Nouveau mot de passe (minimum 8 caractères)", example = "NouveauMot2Passe!", requiredMode = Schema.RequiredMode.REQUIRED, minLength = 8)
    @NotBlank(message = "Le nouveau mot de passe est obligatoire")
    @Size(min = 8, message = "Le nouveau mot de passe doit contenir au moins 8 caractères")
    private String newPassword;

    @Schema(description = "Confirmation du nouveau mot de passe", example = "NouveauMot2Passe!", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La confirmation du mot de passe est obligatoire")
    private String confirmPassword;
}