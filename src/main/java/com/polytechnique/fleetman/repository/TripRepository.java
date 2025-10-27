package com.polytechnique.fleetman.repository;

import com.polytechnique.fleetman.entity.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<TripEntity, Long> {

    List<TripEntity> findByVehicle_VehicleId(Long vehicleId);

    List<TripEntity> findByDriver_DriverId(Long driverId);

    List<TripEntity> findByDepartureDateTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT t FROM TripEntity t WHERE t.arrivalDateTime IS NULL")
    List<TripEntity> findOngoingTrips();

    @Query("SELECT t FROM TripEntity t WHERE t.vehicle.vehicleId = :vehicleId AND t.arrivalDateTime IS NULL")
    List<TripEntity> findOngoingTripsByVehicle(@Param("vehicleId") Long vehicleId);
}