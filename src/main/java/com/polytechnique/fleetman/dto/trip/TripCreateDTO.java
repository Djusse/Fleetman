package com.polytechnique.fleetman.dto.trip;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Données nécessaires pour créer un trajet")
public class TripCreateDTO {

    @Schema(
            description = "Coordonnées du point de départ (longitude, latitude)",
            example = "{\"type\": \"Point\", \"coordinates\": [2.3522, 48.8566]}"
    )
    @NotNull(message = "Le point de départ est obligatoire")
    private Point departurePoint;

    @Schema(
            description = "Coordonnées du point d’arrivée (longitude, latitude)",
            example = "{\"type\": \"Point\", \"coordinates\": [2.3522, 48.8566]}"
    )
    @NotNull(message = "Le point d'arrivée est obligatoire")
    private Point arrivalPoint;

    @Schema(
            description = "Date et heure de départ du trajet",
            example = "2025-10-28T08:30:00"
    )
    @NotNull(message = "La date/heure de départ est obligatoire")
    private LocalDateTime departureDateTime;

    @Schema(
            description = "Date et heure d’arrivée prévue ou réelle du trajet",
            example = "2025-10-28T13:45:00"
    )
    private LocalDateTime arrivalDateTime;

    @Schema(description = "Identifiant du conducteur assigné", example = "1")
    @NotNull(message = "L'ID du conducteur est obligatoire")
    private Long driverId;

    @Schema(description = "Identifiant du véhicule utilisé", example = "12")
    @NotNull(message = "L'ID du véhicule est obligatoire")
    private Long vehicleId;
}
