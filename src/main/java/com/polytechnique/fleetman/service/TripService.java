package com.polytechnique.fleetman.service;

import com.polytechnique.fleetman.dto.trip.TripCreateDTO;
import com.polytechnique.fleetman.dto.trip.TripDTO;
import com.polytechnique.fleetman.dto.trip.TripUpdateDTO;
import com.polytechnique.fleetman.entity.DriverEntity;
import com.polytechnique.fleetman.entity.TripEntity;
import com.polytechnique.fleetman.entity.VehicleEntity;
import com.polytechnique.fleetman.exception.ResourceNotFoundException;
import com.polytechnique.fleetman.repository.DriverRepository;
import com.polytechnique.fleetman.repository.TripRepository;
import com.polytechnique.fleetman.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public TripDTO createTrip(TripCreateDTO tripCreateDTO) {
        DriverEntity driver = driverRepository.findById(tripCreateDTO.getDriverId())
                .orElseThrow(() -> new ResourceNotFoundException("Conducteur non trouvé"));

        VehicleEntity vehicle = vehicleRepository.findById(tripCreateDTO.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé"));

        Point departurePoint = tripCreateDTO.getDeparturePoint() ;
        Point arrivalPoint = tripCreateDTO.getArrivalPoint() ;
        TripEntity trip = new TripEntity();
        trip.setDeparturePoint(departurePoint);
        trip.setArrivalPoint(arrivalPoint);
        trip.setDepartureDateTime(tripCreateDTO.getDepartureDateTime());
        trip.setArrivalDateTime(tripCreateDTO.getArrivalDateTime());
        trip.setDriver(driver);
        trip.setVehicle(vehicle);

        TripEntity saved = tripRepository.save(trip);
        return convertToDTO(saved);
    }

    @Transactional(readOnly = true)
    public TripDTO getTripById(Long tripId) {
        TripEntity trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trajet non trouvé"));
        return convertToDTO(trip);
    }

    @Transactional(readOnly = true)
    public List<TripDTO> getAllTrips() {
        return tripRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TripDTO> getTripsByVehicleId(Long vehicleId) {

        // vérifions si le vehicule existe
        VehicleEntity vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé"));

        return tripRepository.findByVehicle_VehicleId(vehicleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TripDTO> getTripsByDriverId(Long driverId) {

        // vérifions si le conducteur existe
        DriverEntity driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Conducteur non trouvé"));

        return tripRepository.findByDriver_DriverId(driverId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TripDTO> getOngoingTrips() {
        return tripRepository.findOngoingTrips().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TripDTO updateTrip(Long tripId, TripUpdateDTO tripUpdateDTO) {
        TripEntity trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trajet non trouvé"));

        if (tripUpdateDTO.getArrivalPoint() != null) {
            trip.setArrivalPoint(tripUpdateDTO.getArrivalPoint());
        }

        if (tripUpdateDTO.getArrivalDateTime() != null) {
            trip.setArrivalDateTime(tripUpdateDTO.getArrivalDateTime());
        }

        TripEntity updated = tripRepository.save(trip);
        return convertToDTO(updated);
    }

    @Transactional
    public void deleteTrip(Long tripId) {
        if (!tripRepository.existsById(tripId)) {
            throw new ResourceNotFoundException("Trajet non trouvé");
        }
        tripRepository.deleteById(tripId);
    }

    private TripDTO convertToDTO(TripEntity trip) {
        TripDTO dto = new TripDTO();
        dto.setTripId(trip.getTripId());
        dto.setDeparturePoint(trip.getDeparturePoint());
        dto.setArrivalPoint(trip.getArrivalPoint());
        dto.setDepartureDateTime(trip.getDepartureDateTime());
        dto.setArrivalDateTime(trip.getArrivalDateTime());
        dto.setDriverId(trip.getDriver().getDriverId());
        dto.setDriverName(trip.getDriver().getDriverName());
        dto.setVehicleId(trip.getVehicle().getVehicleId());
        dto.setVehicleName(trip.getVehicle().getVehicleName());
        return dto;
    }
}