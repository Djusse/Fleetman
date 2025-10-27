package com.polytechnique.fleetman.dto.drivervehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverVehicleDTO {
    private Long driverId;
    private String driverName;
    private Long vehicleId;
    private String vehicleName;
    private String vehicleRegistrationNumber;
    private LocalDateTime assignedAt;
}