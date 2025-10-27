package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.trip.TripCreateDTO;
import com.polytechnique.fleetman.dto.trip.TripDTO;
import com.polytechnique.fleetman.dto.trip.TripUpdateDTO;
import com.polytechnique.fleetman.service.TripService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping
    public ResponseEntity<TripDTO> createTrip(@Valid @RequestBody TripCreateDTO tripCreateDTO) {
        TripDTO createdTrip = tripService.createTrip(tripCreateDTO);
        return new ResponseEntity<>(createdTrip, HttpStatus.CREATED);
    }

    @GetMapping("/{tripId}")
    public ResponseEntity<TripDTO> getTripById(@PathVariable Long tripId) {
        TripDTO trip = tripService.getTripById(tripId);
        return ResponseEntity.ok(trip);
    }

    @GetMapping
    public ResponseEntity<List<TripDTO>> getAllTrips() {
        List<TripDTO> trips = tripService.getAllTrips();
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<TripDTO>> getTripsByVehicleId(@PathVariable Long vehicleId) {
        List<TripDTO> trips = tripService.getTripsByVehicleId(vehicleId);
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<TripDTO>> getTripsByDriverId(@PathVariable Long driverId) {
        List<TripDTO> trips = tripService.getTripsByDriverId(driverId);
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/ongoing")
    public ResponseEntity<List<TripDTO>> getOngoingTrips() {
        List<TripDTO> trips = tripService.getOngoingTrips();
        return ResponseEntity.ok(trips);
    }

    @PutMapping("/{tripId}")
    public ResponseEntity<TripDTO> updateTrip(
            @PathVariable Long tripId,
            @Valid @RequestBody TripUpdateDTO tripUpdateDTO) {
        TripDTO updatedTrip = tripService.updateTrip(tripId, tripUpdateDTO);
        return ResponseEntity.ok(updatedTrip);
    }

    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long tripId) {
        tripService.deleteTrip(tripId);
        return ResponseEntity.noContent().build();
    }
}