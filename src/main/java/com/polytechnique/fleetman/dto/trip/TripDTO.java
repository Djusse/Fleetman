package com.polytechnique.fleetman.dto.trip;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TripDTO {
    private Long tripId;
    private Point departurePoint;
    private Point arrivalPoint;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private Long driverId;
    private String driverName;
    private Long vehicleId;
    private String vehicleName;
}