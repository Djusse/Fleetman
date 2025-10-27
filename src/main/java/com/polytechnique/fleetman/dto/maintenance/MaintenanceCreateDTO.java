package com.polytechnique.fleetman.dto.maintenance;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceCreateDTO {

    @NotBlank(message = "Le sujet de la maintenance est obligatoire")
    @Size(max = 200)
    private String maintenanceSubject;

    @NotNull(message = "Le coût est obligatoire")
    @DecimalMin(value = "0.0", message = "Le coût doit être >= 0")
    private BigDecimal maintenanceCost;

    private Point maintenancePoint;

    @NotNull(message = "L'ID du véhicule est obligatoire")
    private Long vehicleId;
}