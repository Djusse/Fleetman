package com.polytechnique.fleetman.dto.maintenance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO représentant une opération de maintenance")
public class MaintenanceDTO {

    @Schema(description = "ID unique de la maintenance", example = "1")
    private Long maintenanceId;

    @Schema(description = "Date et heure de la maintenance", example = "2024-01-15T10:30:00")
    private LocalDateTime maintenanceDateTime;

    @Schema(
            description = "Position GPS de la maintenance",
            example = "{\"type\": \"Point\", \"coordinates\": [2.3522, 48.8566]}"
    )
    private Point maintenancePoint;

    @Schema(description = "Sujet ou description de la maintenance", example = "Vidange et remplacement des filtres")
    private String maintenanceSubject;

    @Schema(description = "Coût total de la maintenance", example = "150.75")
    private BigDecimal maintenanceCost;

    @Schema(description = "ID du véhicule", example = "1")
    private Long vehicleId;

    @Schema(description = "Nom du véhicule", example = "Renault Trafic")
    private String vehicleName;
}