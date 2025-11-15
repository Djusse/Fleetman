package com.polytechnique.fleetman.dto.driver;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Données pour mettre à jour un conducteur existant. Tous les champs sont optionnels.")
public class DriverUpdateDTO {

    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    @Schema(
            description = "Nom complet du conducteur",
            example = "Jean-Pierre Dupont",
            maxLength = 100
    )
    private String driverName;

    @Email(message = "Format d'email invalide")
    @Size(max = 150, message = "L'email ne doit pas dépasser 150 caractères")
    @Schema(
            description = "Adresse email professionnelle du conducteur",
            example = "jp.dupont@fleetman.com",
            format = "email",
            maxLength = 150
    )
    private String driverEmail;

    @Size(max = 20, message = "Le numéro ne doit pas dépasser 20 caractères")
    @Schema(
            description = "Numéro de téléphone du conducteur",
            example = "+237 6 12 34 56 99",
            maxLength = 20
    )
    private String driverPhoneNumber;

    @Size(max = 100, message = "Le nom du contact d'urgence ne doit pas dépasser 100 caractères")
    @Schema(
            description = "Nom du contact d'urgence",
            example = "Sophie Dupont",
            maxLength = 100
    )
    private String emergencyContactName;

    @Size(max = 20, message = "Le contact d'urgence ne doit pas dépasser 20 caractères")
    @Schema(
            description = "Numéro de téléphone du contact d'urgence",
            example = "+237 6 88 77 66 55",
            maxLength = 20
    )
    private String emergencyContact;

    @Schema(
            description = "Informations personnelles supplémentaires ou notes concernant le conducteur",
            example = "Permis B et C, spécialisé en transport longue distance"
    )
    private String personalInformations;
}