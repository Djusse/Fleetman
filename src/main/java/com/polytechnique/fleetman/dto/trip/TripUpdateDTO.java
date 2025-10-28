package com.polytechnique.fleetman.dto.trip;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Données modifiables d’un trajet existant")
public class TripUpdateDTO {

    @Schema(
            description = "Nouvelles coordonnées du point d’arrivée",
            example = "{\"type\": \"Point\", \"coordinates\": [2.3522, 48.8566]}"
    )
    private Point arrivalPoint;

    @Schema(
            description = "Nouvelle date/heure d’arrivée du trajet",
            example = "2025-10-28T14:00:00"
    )
    private LocalDateTime arrivalDateTime;
}
