package com.polytechnique.fleetman.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fuel_recharge")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuelRechargeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rechargeId;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal rechargeQuantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal rechargePrice;

    @Column(nullable = false)
    private Point rechargePoint;

    @Column(nullable = false)
    private LocalDateTime rechargeDateTime = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private VehicleEntity vehicle;
}
