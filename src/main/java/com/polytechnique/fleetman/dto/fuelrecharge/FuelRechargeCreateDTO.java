package com.polytechnique.fleetman.dto.fuelrecharge;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelRechargeCreateDTO {

    @NotNull(message = "La quantité est obligatoire")
    @DecimalMin(value = "0.01", message = "La quantité doit être > 0")
    private BigDecimal rechargeQuantity;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", message = "Le prix doit être >= 0")
    private BigDecimal rechargePrice;

    private Point rechargePoint;

    @NotNull(message = "L'ID du véhicule est obligatoire")
    private Long vehicleId;
}