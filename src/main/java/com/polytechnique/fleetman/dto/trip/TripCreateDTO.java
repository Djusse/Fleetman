package com.polytechnique.fleetman.dto.trip;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripCreateDTO {

    @NotNull(message = "Le point de départ est obligatoire")
    private Point departurePoint;

    @NotNull(message = "Le Point d'arrivée est obligatoire")
    private Point arrivalPoint;

    @NotNull(message = "La date/heure de départ est obligatoire")
    private LocalDateTime departureDateTime;

    private LocalDateTime arrivalDateTime;

    @NotNull(message = "L'ID du conducteur est obligatoire")
    private Long driverId;

    @NotNull(message = "L'ID du véhicule est obligatoire")
    private Long vehicleId;
}