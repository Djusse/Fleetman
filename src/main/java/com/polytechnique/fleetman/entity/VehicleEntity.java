package com.polytechnique.fleetman.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    @Column(nullable = false, length = 50)
    private String vehicleMake;

    @Column(nullable = false, length = 100)
    private String vehicleName;

    @Column(nullable = false, unique = true, length = 20)
    private String vehicleRegistrationNumber;

    @Column(nullable = false, length = 50)
    private String vehicleType;

    @Column(columnDefinition = "TEXT")
    private String vehicleImage;

    @Column(columnDefinition = "TEXT")
    private String vehicleDocument;

    @Column(length = 100)
    private String vehicleIotAddress;

    @Column(precision = 5, scale = 2)
    private BigDecimal vehicleFuelLevel;

    @Column
    private Integer vehicleNumberPassengers;

    @Column(precision = 6, scale = 2)
    private BigDecimal vehicleSpeed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Relations
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FuelRechargeEntity> fuelRecharges;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaintenanceEntity> maintenances;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PositionEntity> positions;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PositionHistoryEntity> positionHistories;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TripEntity> trips;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DriverVehicleEntity> driverVehicles;
}