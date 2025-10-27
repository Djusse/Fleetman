package com.polytechnique.fleetman.dto.trip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripUpdateDTO {
    private Point arrivalPoint;
    private LocalDateTime arrivalDateTime;
}
