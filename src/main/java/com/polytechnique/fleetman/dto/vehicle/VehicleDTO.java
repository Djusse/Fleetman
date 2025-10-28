package com.polytechnique.fleetman.dto.vehicle;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Représentation complète d'un véhicule de la flotte")
public class VehicleDTO {

    @Schema(
            description = "Identifiant unique du véhicule",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long vehicleId;

    @Schema(
            description = "Marque du véhicule",
            example = "Toyota"
    )
    private String vehicleMake;

    @Schema(
            description = "Modèle ou nom du véhicule",
            example = "Hilux 4x4"
    )
    private String vehicleName;

    @Schema(
            description = "Numéro d'immatriculation unique",
            example = "AB-123-CD"
    )
    private String vehicleRegistrationNumber;

    @Schema(
            description = "Type de véhicule",
            example = "Camion"
    )
    private String vehicleType;

    @Schema(
            description = "URL ou chemin de l'image du véhicule",
            example = "/images/vehicles/toyota-hilux-123.jpg"
    )
    private String vehicleImage;

    @Schema(
            description = "URL ou chemin du document d'immatriculation",
            example = "/documents/vehicles/registration-123.pdf"
    )
    private String vehicleDocument;

    @Schema(
            description = "Adresse IoT du dispositif de suivi",
            example = "iot://device-tracker-12345.fleetman.com"
    )
    private String vehicleIotAddress;

    @Schema(
            description = "Niveau de carburant actuel en pourcentage",
            example = "75.5"
    )
    private BigDecimal vehicleFuelLevel;

    @Schema(
            description = "Capacité maximale de passagers",
            example = "5"
    )
    private Integer vehicleNumberPassengers;

    @Schema(
            description = "Vitesse actuelle en km/h",
            example = "65.0"
    )
    private BigDecimal vehicleSpeed;

    @Schema(
            description = "ID de l'utilisateur responsable du véhicule",
            example = "1"
    )
    private Long userId;

    @Schema(
            description = "Date et heure de création de l'enregistrement",
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