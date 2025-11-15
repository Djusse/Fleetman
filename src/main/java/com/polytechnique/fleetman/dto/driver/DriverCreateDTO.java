package com.polytechnique.fleetman.dto.driver;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Données nécessaires pour créer un nouveau conducteur")
public class DriverCreateDTO {

    @NotBlank(message = "Le nom du conducteur est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    @Schema(
            description = "Nom complet du conducteur",
            example = "Jean Dupont",
            requiredMode = Schema.RequiredMode.REQUIRED,
            maxLength = 100
    )
    private String driverName;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    @Size(max = 150, message = "L'email ne doit pas dépasser 150 caractères")
    @Schema(
            description = "Adresse email professionnelle du conducteur",
            example = "jean.dupont@fleetman.com",
            requiredMode = Schema.RequiredMode.REQUIRED,
            format = "email",
            maxLength = 150
    )
    private String driverEmail;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Size(max = 20, message = "Le numéro ne doit pas dépasser 20 caractères")
    @Schema(
            description = "Numéro de téléphone du conducteur",
            example = "+237 6 12 34 56 78",
            requiredMode = Schema.RequiredMode.REQUIRED,
            maxLength = 20
    )
    private String driverPhoneNumber;

    @Size(max = 100, message = "Le nom du contact d'urgence ne doit pas dépasser 100 caractères")
    @Schema(
            description = "Nom du contact d'urgence",
            example = "Marie Dupont",
            maxLength = 100
    )
    private String emergencyContactName;

    @Size(max = 20, message = "Le contact d'urgence ne doit pas dépasser 20 caractères")
    @Schema(
            description = "Numéro de téléphone du contact d'urgence",
            example = "+237 6 98 76 54 32",
            maxLength = 20
    )
    private String emergencyContact;

    @Schema(
            description = "Informations personnelles supplémentaires ou notes concernant le conducteur",
            example = "Permis B, 10 ans d'expérience"
    )
    private String personalInformations;
}