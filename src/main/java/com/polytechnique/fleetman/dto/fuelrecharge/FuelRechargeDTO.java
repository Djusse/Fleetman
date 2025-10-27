package com.polytechnique.fleetman.dto.fuelrecharge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelRechargeDTO {
    private Long rechargeId;
    private BigDecimal rechargeQuantity;
    private BigDecimal rechargePrice;
    private Point rechargePoint;
    private LocalDateTime rechargeDateTime;
    private Long vehicleId;
    private String vehicleName;
}