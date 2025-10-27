package com.polytechnique.fleetman.repository;

import com.polytechnique.fleetman.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findByVehicleRegistrationNumber(String registrationNumber);

    List<VehicleEntity> findByUser_UserId(Long userId);

    List<VehicleEntity> findByVehicleType(String vehicleType);

    boolean existsByVehicleRegistrationNumber(String registrationNumber);

    @Query("SELECT v FROM VehicleEntity v WHERE v.vehicleFuelLevel < :threshold")
    List<VehicleEntity> findVehiclesWithLowFuel(@Param("threshold") java.math.BigDecimal threshold);
}
