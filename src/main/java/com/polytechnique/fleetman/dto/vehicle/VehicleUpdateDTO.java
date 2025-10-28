package com.polytechnique.fleetman.dto.vehicle;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Données pour mettre à jour un véhicule existant. Tous les champs sont optionnels.")
public class VehicleUpdateDTO {

    @Size(max = 50)
    @Schema(
            description = "Marque du véhicule",
            example = "Ford",
            maxLength = 50
    )
    private String vehicleMake;

    @Size(max = 100)
    @Schema(
            description = "Modèle ou nom du véhicule",
            example = "Ranger Raptor",
            maxLength = 100
    )
    private String vehicleName;

    @Size(max = 50)
    @Schema(
            description = "Type de véhicule",
            example = "Pick-up",
            maxLength = 50
    )
    private String vehicleType;

    @Schema(
            description = "URL ou chemin de l'image du véhicule",
            example = "/images/vehicles/ford-ranger-456.jpg"
    )
    private String vehicleImage;

    @Schema(
            description = "URL ou chemin du document d'immatriculation",
            example = "/documents/vehicles/registration-456.pdf"
    )
    private String vehicleDocument;

    @Size(max = 100)
    @Schema(
            description = "Adresse IoT du dispositif de suivi",
            example = "iot://device-tracker-67890.fleetman.com",
            maxLength = 100
    )
    private String vehicleIotAddress;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Schema(
            description = "Niveau de carburant actuel en pourcentage",
            example = "50.0",
            minimum = "0",
            maximum = "100"
    )
    private BigDecimal vehicleFuelLevel;

    @Min(value = 1)
    @Schema(
            description = "Capacité maximale de passagers",
            example = "7",
            minimum = "1"
    )
    private Integer vehicleNumberPassagers;

    @DecimalMin(value = "0.0")
    @Schema(
            description = "Vitesse actuelle du véhicule en km/h",
            example = "65.0",
            minimum = "0"
    )
    private BigDecimal vehicleSpeed;
}