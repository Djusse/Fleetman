package com.polytechnique.fleetman.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "driver")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;

    @Column( nullable = false, length = 100)
    private String driverName;

    @Column( nullable = false, unique = true, length = 150)
    private String driverEmail;

    @Column( nullable = false, length = 20)
    private String driverPhoneNumber;

    @Column( length = 100)
    private String emergencyContactName;

    @Column( length = 20)
    private String emergencyContact;

    @Column( columnDefinition = "TEXT")
    private String personalInformations;

    @CreationTimestamp
    @Column( nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column( nullable = false)
    private LocalDateTime updatedAt;

    // Relation Many-to-Many avec Vehicle via driver_vehicle
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DriverVehicleEntity> driverVehicles;
}
