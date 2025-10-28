package com.polytechnique.fleetman.dto.position;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO représentant un enregistrement de position GPS")
public class PositionDTO {

    @Schema(description = "ID unique de l'enregistrement de position", example = "1")
    private Long positionId;

    @Schema(
            description = "Coordonnées GPS de la position",
            example = "{\"type\": \"Point\", \"coordinates\": [2.3522, 48.8566]}"
    )
    private Point coordinate;

    @Schema(description = "Date et heure d'enregistrement de la position", example = "2024-01-15T14:30:00")
    private LocalDateTime positionDateTime;

    @Schema(description = "ID du véhicule", example = "1")
    private Long vehicleId;

    @Schema(description = "Nom du véhicule", example = "Renault Trafic")
    private String vehicleName;
}