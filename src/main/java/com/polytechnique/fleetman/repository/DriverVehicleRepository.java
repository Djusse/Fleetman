package com.polytechnique.fleetman.repository;

import com.polytechnique.fleetman.entity.DriverEntity;
import com.polytechnique.fleetman.entity.DriverVehicleEntity;
import com.polytechnique.fleetman.entity.DriverVehicleId;
import com.polytechnique.fleetman.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverVehicleRepository extends JpaRepository<DriverVehicleEntity, DriverVehicleId> {

    List<DriverVehicleEntity> findByDriver_DriverId(Long driverId);

    List<DriverVehicleEntity> findByVehicle_VehicleId(Long vehicleId);

    boolean existsByDriver_DriverIdAndVehicle_VehicleId(Long driverId, Long vehicleId);

    Optional<DriverVehicleEntity> findByDriverAndVehicle(DriverEntity driver, VehicleEntity vehicle);
}