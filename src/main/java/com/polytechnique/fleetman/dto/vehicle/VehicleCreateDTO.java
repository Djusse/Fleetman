package com.polytechnique.fleetman.dto.vehicle;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleCreateDTO {

    @NotBlank(message = "La marque du véhicule est obligatoire")
    @Size(max = 50)
    private String vehicleMake;

    @NotBlank(message = "Le nom du véhicule est obligatoire")
    @Size(max = 100)
    private String vehicleName;

    @NotBlank(message = "Le numéro d'immatriculation est obligatoire")
    @Size(max = 20)
    private String vehicleRegistrationNumber;

    @NotBlank(message = "Le type de véhicule est obligatoire")
    @Size(max = 50)
    private String vehicleType;

    private String vehicleImage;
    private String vehicleDocument;

    @Size(max = 100)
    private String vehicleIotAddress;

    @DecimalMin(value = "0.0", message = "Le niveau de carburant doit être >= 0")
    @DecimalMax(value = "100.0", message = "Le niveau de carburant doit être <= 100")
    private BigDecimal vehicleFuelLevel;

    @Min(value = 1, message = "Le nombre de passagers doit être >= 1")
    private Integer vehicleNumberPassengers;

    @DecimalMin(value = "0.0", message = "La vitesse doit être >= 0")
    private BigDecimal vehicleSpeed;

    @NotNull(message = "L'ID utilisateur est obligatoire")
    private Long userId;
}