package com.polytechnique.fleetman.dto.position;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO pour la création d'un enregistrement de position GPS")
public class PositionCreateDTO {

    @NotNull(message = "La position est obligatoire")
    @Schema(
            description = "Coordonnées GPS de la position (format WGS84)",
            example = "{\"type\": \"Point\", \"coordinates\": [2.3522, 48.8566]}",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Point coordinate;

    @NotNull(message = "L'ID du véhicule est obligatoire")
    @Schema(
            description = "ID du véhicule concerné",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long vehicleId;
}