package com.polytechnique.fleetman.dto.maintenance;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO pour la création d'une opération de maintenance")
public class MaintenanceCreateDTO {

    @NotBlank(message = "Le sujet de la maintenance est obligatoire")
    @Size(max = 200)
    @Schema(
            description = "Sujet ou description de la maintenance effectuée",
            example = "Vidange et remplacement des filtres",
            requiredMode = Schema.RequiredMode.REQUIRED,
            maxLength = 200
    )
    private String maintenanceSubject;

    @NotNull(message = "Le coût est obligatoire")
    @DecimalMin(value = "0.0", message = "Le coût doit être >= 0")
    @Schema(
            description = "Coût total de l'opération de maintenance",
            example = "150.75",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minimum = "0.0"
    )
    private BigDecimal maintenanceCost;

    @Schema(
            description = "Position GPS où la maintenance a été effectuée",
            example = "{\"type\": \"Point\", \"coordinates\": [2.3522, 48.8566]}",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private Point maintenancePoint;

    @NotNull(message = "L'ID du véhicule est obligatoire")
    @Schema(
            description = "ID du véhicule concerné par la maintenance",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long vehicleId;
}