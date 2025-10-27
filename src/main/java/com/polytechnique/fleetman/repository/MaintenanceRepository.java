package com.polytechnique.fleetman.repository;

import com.polytechnique.fleetman.entity.MaintenanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<MaintenanceEntity, Long> {

    List<MaintenanceEntity> findByVehicle_VehicleId(Long vehicleId);

    List<MaintenanceEntity> findByMaintenanceDateTimeBetween(LocalDateTime start, LocalDateTime end);

    List<MaintenanceEntity> findByVehicle_VehicleIdOrderByMaintenanceDateTimeDesc(Long vehicleId);
}