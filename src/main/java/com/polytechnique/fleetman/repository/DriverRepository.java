package com.polytechnique.fleetman.repository;

import com.polytechnique.fleetman.entity.DriverEntity;
import com.polytechnique.fleetman.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {
    Optional<DriverEntity> findByDriverEmail(String driverEmail);
    boolean existsByDriverEmail(String driverEmail);
    List<DriverEntity> findByUser_UserId(Long userId);

}
