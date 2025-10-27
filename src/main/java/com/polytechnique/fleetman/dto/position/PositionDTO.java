package com.polytechnique.fleetman.dto.position;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO {
    private Long positionId;
    private Point coordinate;
    private LocalDateTime positionDateTime;
    private Long vehicleId;
    private String vehicleName;
}