package com.polytechnique.fleetman.repository;

import com.polytechnique.fleetman.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {
    Optional<DriverEntity> findByDriverEmail(String driverEmail);
    boolean existsByDriverEmail(String driverEmail);
}
