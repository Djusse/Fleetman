package com.polytechnique.fleetman.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maintenanceId;

    @Column(nullable = false)
    private LocalDateTime maintenanceDateTime = LocalDateTime.now();

    @Column(nullable = false)
    private Point maintenancePoint;

    @Column(nullable = false, length = 200)
    private String maintenanceSubject;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal maintenanceCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;
}
