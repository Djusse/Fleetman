package com.polytechnique.fleetman.dto.drivervehicle;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Représentation complète d'une affectation conducteur-véhicule avec informations détaillées")
public class DriverVehicleDTO {

    @Schema(
            description = "Identifiant du conducteur",
            example = "1"
    )
    private Long driverId;

    @Schema(
            description = "Nom complet du conducteur",
            example = "Jean Dupont"
    )
    private String driverName;

    @Schema(
            description = "Identifiant du véhicule",
            example = "5"
    )
    private Long vehicleId;

    @Schema(
            description = "Nom/Modèle du véhicule",
            example = "Toyota Hilux 4x4"
    )
    private String vehicleName;

    @Schema(
            description = "Numéro d'immatriculation du véhicule",
            example = "AB-123-CD"
    )
    private String vehicleRegistrationNumber;

    @Schema(
            description = "Date et heure de l'affectation",
            example = "2025-01-15T10:30:00",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime assignedAt;
}