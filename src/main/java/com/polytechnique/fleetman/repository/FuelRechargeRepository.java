package com.polytechnique.fleetman.repository;

import com.polytechnique.fleetman.entity.FuelRechargeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FuelRechargeRepository extends JpaRepository<FuelRechargeEntity, Long> {

    List<FuelRechargeEntity> findByVehicle_VehicleId(Long vehicleId);

    List<FuelRechargeEntity> findByRechargeDateTimeBetween(LocalDateTime start, LocalDateTime end);

    List<FuelRechargeEntity> findByVehicle_VehicleIdOrderByRechargeDateTimeDesc(Long vehicleId);
}