package com.polytechnique.fleetman.dto.maintenance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceDTO {
    private Long maintenanceId;
    private LocalDateTime maintenanceDateTime;
    private Point maintenancePoint;
    private String maintenanceSubject;
    private BigDecimal maintenanceCost;
    private Long vehicleId;
    private String vehicleName;
}