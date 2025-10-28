package com.polytechnique.fleetman.dto.fuelrecharge;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO pour la création d'une recharge de carburant")
public class FuelRechargeCreateDTO {

    @NotNull(message = "La quantité est obligatoire")
    @DecimalMin(value = "0.01", message = "La quantité doit être > 0")
    @Schema(
            description = "Quantité de carburant rechargée (en litres)",
            example = "45.50",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minimum = "0.01"
    )
    private BigDecimal rechargeQuantity;

    @NotNull(message = "Le prix est obligatoire")
    @DecimalMin(value = "0.0", message = "Le prix doit être >= 0")
    @Schema(
            description = "Prix total de la recharge",
            example = "68.25",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minimum = "0.0"
    )
    private BigDecimal rechargePrice;

    @Schema(
            description = "Position GPS où la recharge a été effectuée (coordonnées WGS84)",
            example = "{\"type\": \"Point\", \"coordinates\": [2.3522, 48.8566]}",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Point rechargePoint;

    @NotNull(message = "L'ID du véhicule est obligatoire")
    @Schema(
            description = "ID du véhicule concerné par la recharge",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long vehicleId;
}