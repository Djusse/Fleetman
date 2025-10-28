package com.polytechnique.fleetman.dto.fuelrecharge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO représentant une recharge de carburant")
public class FuelRechargeDTO {

    @Schema(description = "ID unique de la recharge", example = "1")
    private Long rechargeId;

    @Schema(description = "Quantité de carburant rechargée (en litres)", example = "45.50")
    private BigDecimal rechargeQuantity;

    @Schema(description = "Prix total de la recharge", example = "68.25")
    private BigDecimal rechargePrice;

    @Schema(
            description = "Position GPS de la recharge",
            example = "{\"type\": \"Point\", \"coordinates\": [2.3522, 48.8566]}"
    )
    private Point rechargePoint;

    @Schema(description = "Date et heure de la recharge", example = "2024-01-15T14:30:00")
    private LocalDateTime rechargeDateTime;

    @Schema(description = "ID du véhicule", example = "1")
    private Long vehicleId;

    @Schema(description = "Nom du véhicule", example = "Renault Trafic")
    private String vehicleName;
}