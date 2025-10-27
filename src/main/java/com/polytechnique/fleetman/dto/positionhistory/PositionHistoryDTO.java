package com.polytechnique.fleetman.dto.positionhistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.LineString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionHistoryDTO {
    private Long positionHistoryId;
    private LineString summaryCoordinate;
    private LocalDateTime positionDateTime;
    private Long vehicleId;
    private String vehicleName;
}