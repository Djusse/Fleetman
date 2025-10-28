package com.polytechnique.fleetman.dto.drivervehicle;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Données nécessaires pour créer une affectation conducteur-véhicule")
public class DriverVehicleCreateDTO {

    @NotNull(message = "L'ID du conducteur est obligatoire")
    @Schema(
            description = "Identifiant du conducteur à assigner",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long driverId;

    @NotNull(message = "L'ID du véhicule est obligatoire")
    @Schema(
            description = "Identifiant du véhicule à assigner",
            example = "5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long vehicleId;
}