package com.polytechnique.fleetman.dto.position;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionCreateDTO {

    @NotNull(message = "La position est obligatoire")
    private Point coordinate;

    @NotNull(message = "L'ID du véhicule est obligatoire")
    private Long vehicleId;
}