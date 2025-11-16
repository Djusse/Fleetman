package com.polytechnique.fleetman.dto.driver;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Représentation complète d'un conducteur")
public class DriverDTO {

    @Schema(
            description = "Identifiant unique du conducteur",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long driverId;

    @Schema(
            description = "Nom complet du conducteur",
            example = "Jean Dupont"
    )
    private String driverName;

    @Schema(
            description = "Adresse email professionnelle du conducteur",
            example = "jean.dupont@fleetman.com",
            format = "email"
    )
    private String driverEmail;

    @Schema(
            description = "Numéro de téléphone du conducteur",
            example = "+237 6 12 34 56 78"
    )
    private String driverPhoneNumber;

    @Schema(
            description = "Nom du contact d'urgence",
            example = "Marie Dupont"
    )
    private String emergencyContactName;

    @Schema(
            description = "Numéro de téléphone du contact d'urgence",
            example = "+237 6 98 76 54 32"
    )
    private String emergencyContact;

    @Schema(
            description = "Informations personnelles supplémentaires",
            example = "Permis B, 10 ans d'expérience"
    )
    private String personalInformations;

    @Schema(
            description = "ID de l'utilisateur responsable du véhicule",
            example = "1"
    )
    private Long userId;

    @Schema(
            description = "Date et heure de création du conducteur",
            example = "2025-01-15T10:30:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Date et heure de la dernière mise à jour",
            example = "2025-01-20T14:45:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime updatedAt;
}