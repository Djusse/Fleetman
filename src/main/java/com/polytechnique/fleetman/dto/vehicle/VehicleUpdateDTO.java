package com.polytechnique.fleetman.dto.vehicle;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleUpdateDTO {

    @Size(max = 50)
    private String vehicleMake;

    @Size(max = 100)
    private String vehicleName;

    @Size(max = 50)
    private String vehicleType;

    private String vehicleImage;
    private String vehicleDocument;

    @Size(max = 100)
    private String vehicleIotAddress;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private BigDecimal vehicleFuelLevel;

    @Min(value = 1)
    private Integer vehicleNumberPassengers;

    @DecimalMin(value = "0.0")
    private BigDecimal vehicleSpeed;
}