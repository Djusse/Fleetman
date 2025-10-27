package com.polytechnique.fleetman.repository;

import com.polytechnique.fleetman.entity.PositionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PositionHistoryRepository extends JpaRepository<PositionHistoryEntity, Long> {

    List<PositionHistoryEntity> findByVehicle_VehicleId(Long vehicleId);

    List<PositionHistoryEntity> findByPositionDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
