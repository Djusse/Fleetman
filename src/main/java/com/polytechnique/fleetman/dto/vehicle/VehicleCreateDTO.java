package com.polytechnique.fleetman.dto.vehicle;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Données nécessaires pour créer un nouveau véhicule dans la flotte")
public class VehicleCreateDTO {

    @NotBlank(message = "La marque du véhicule est obligatoire")
    @Size(max = 50)
    @Schema(
            description = "Marque du véhicule",
            example = "Toyota",
            requiredMode = Schema.RequiredMode.REQUIRED,
            maxLength = 50
    )
    private String vehicleMake;

    @NotBlank(message = "Le nom du véhicule est obligatoire")
    @Size(max = 100)
    @Schema(
            description = "Modèle ou nom du véhicule",
            example = "Hilux 4x4",
            requiredMode = Schema.RequiredMode.REQUIRED,
            maxLength = 100
    )
    private String vehicleName;

    @NotBlank(message = "Le numéro d'immatriculation est obligatoire")
    @Size(max = 20)
    @Schema(
            description = "Numéro d'immatriculation unique du véhicule",
            example = "AB-123-CD",
            requiredMode = Schema.RequiredMode.REQUIRED,
            maxLength = 20
    )
    private String vehicleRegistrationNumber;

    @NotBlank(message = "Le type de véhicule est obligatoire")
    @Size(max = 50)
    @Schema(
            description = "Type de véhicule (camion, voiture, van, etc.)",
            example = "Camion",
            requiredMode = Schema.RequiredMode.REQUIRED,
            maxLength = 50
    )
    private String vehicleType;

    @Schema(
            description = "URL ou chemin de l'image du véhicule",
            example = "/images/vehicles/toyota-hilux-123.jpg"
    )
    private String vehicleImage;

    @Schema(
            description = "URL ou chemin du document d'immatriculation du véhicule",
            example = "/documents/vehicles/registration-123.pdf"
    )
    private String vehicleDocument;

    @Size(max = 100)
    @Schema(
            description = "Adresse IoT du dispositif de suivi installé dans le véhicule",
            example = "iot://device-tracker-12345.fleetman.com",
            maxLength = 100
    )
    private String vehicleIotAddress;

    @DecimalMin(value = "0.0", message = "Le niveau de carburant doit être >= 0")
    @DecimalMax(value = "100.0", message = "Le niveau de carburant doit être <= 100")
    @Schema(
            description = "Niveau de carburant actuel en pourcentage",
            example = "75.5",
            minimum = "0",
            maximum = "100"
    )
    private BigDecimal vehicleFuelLevel;

    @Min(value = 1, message = "Le nombre de passagers doit être >= 1")
    @Schema(
            description = "Capacité maximale de passagers",
            example = "5",
            minimum = "1"
    )
    private Integer vehicleNumberPassengers;

    @DecimalMin(value = "0.0", message = "La vitesse doit être >= 0")
    @Schema(
            description = "Vitesse actuelle du véhicule en km/h",
            example = "0.0",
            minimum = "0"
    )
    private BigDecimal vehicleSpeed;

    @NotNull(message = "L'ID utilisateur est obligatoire")
    @Schema(
            description = "ID de l'utilisateur propriétaire ou responsable du véhicule",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long userId;
}