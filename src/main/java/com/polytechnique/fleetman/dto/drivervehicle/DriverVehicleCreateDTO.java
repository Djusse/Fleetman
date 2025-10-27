package com.polytechnique.fleetman.dto.drivervehicle;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverVehicleCreateDTO {

    @NotNull(message = "L'ID du conducteur est obligatoire")
    private Long driverId;

    @NotNull(message = "L'ID du véhicule est obligatoire")
    private Long vehicleId;
}