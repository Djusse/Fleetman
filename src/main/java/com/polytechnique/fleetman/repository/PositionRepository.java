package com.polytechnique.fleetman.repository;

import com.polytechnique.fleetman.entity.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, Long> {

    List<PositionEntity> findByVehicle_VehicleId(Long vehicleId);

    List<PositionEntity> findByPositionDateTimeBetween(LocalDateTime start, LocalDateTime end);

    /**
     * Récupère les 2 dernières positions d'un véhicule
     */
    @Query(value = "SELECT * FROM position WHERE vehicle_id = :vehicleId " +
            "ORDER BY position_date_time DESC LIMIT 2",
            nativeQuery = true)
    List<PositionEntity> findLastTwoPositions(@Param("vehicleId") Long vehicleId);
    @Query("SELECT p FROM PositionEntity p WHERE p.vehicle.vehicleId = :vehicleId ORDER BY p.positionDateTime DESC LIMIT 1")
    Optional<PositionEntity> findLatestPositionByVehicle(@Param("vehicleId") Long vehicleId);
}