package com.polytechnique.fleetman.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private Long vehicleId;
    private String vehicleMake;
    private String vehicleName;
    private String vehicleRegistrationNumber;
    private String vehicleType;
    private String vehicleImage;
    private String vehicleDocument;
    private String vehicleIotAddress;
    private BigDecimal vehicleFuelLevel;
    private Integer vehicleNumberPassengers;
    private BigDecimal vehicleSpeed;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
