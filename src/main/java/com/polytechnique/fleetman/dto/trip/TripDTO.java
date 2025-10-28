package com.polytechnique.fleetman.dto.trip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Données complètes d’un trajet existant")
public class TripDTO {

    @Schema(description = "Identifiant unique du trajet", example = "5")
    private Long tripId;

    @Schema(
            description = "Coordonnées du point de départ ",
            example = "{\"type\": \"Point\", \"coordinates\": [2.3522, 48.8566]}"
    )
    private Point departurePoint;

    @Schema(
            description = "Coordonnées du point d’arrivée",
            example = "{\"type\": \"Point\", \"coordinates\": [2.3522, 48.8566]}"
    )
    private Point arrivalPoint;

    @Schema(
            description = "Date et heure de départ",
            example = "2025-10-28T08:30:00"
    )
    private LocalDateTime departureDateTime;

    @Schema(
            description = "Date et heure d’arrivée",
            example = "2025-10-28T13:45:00"
    )
    private LocalDateTime arrivalDateTime;

    @Schema(description = "Identifiant du conducteur", example = "1")
    private Long driverId;

    @Schema(description = "Nom complet du conducteur", example = "Jean Dupont")
    private String driverName;

    @Schema(description = "Identifiant du véhicule", example = "12")
    private Long vehicleId;

    @Schema(description = "Nom ou immatriculation du véhicule", example = "Camion Renault - 4X4")
    private String vehicleName;
}
