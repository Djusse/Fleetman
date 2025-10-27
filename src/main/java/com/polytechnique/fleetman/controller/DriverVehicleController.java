package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.drivervehicle.DriverVehicleCreateDTO;
import com.polytechnique.fleetman.dto.drivervehicle.DriverVehicleDTO;
import com.polytechnique.fleetman.service.DriverVehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver-vehicle")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DriverVehicleController {

    private final DriverVehicleService driverVehicleService;

    @PostMapping
    public ResponseEntity<DriverVehicleDTO> assignDriverToVehicle(
            @Valid @RequestBody DriverVehicleCreateDTO driverVehicleCreateDTO) {
        DriverVehicleDTO assignment = driverVehicleService.assignDriverToVehicle(driverVehicleCreateDTO);
        return new ResponseEntity<>(assignment, HttpStatus.CREATED);
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<DriverVehicleDTO>> getVehiclesByDriverId(@PathVariable Long driverId) {
        List<DriverVehicleDTO> vehicles = driverVehicleService.getVehiclesByDriverId(driverId);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<DriverVehicleDTO>> getDriversByVehicleId(@PathVariable Long vehicleId) {
        List<DriverVehicleDTO> drivers = driverVehicleService.getDriversByVehicleId(vehicleId);
        return ResponseEntity.ok(drivers);
    }

    @GetMapping
    public ResponseEntity<List<DriverVehicleDTO>> getAllAssignments() {
        List<DriverVehicleDTO> assignments = driverVehicleService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    @DeleteMapping("/driver/{driverId}/vehicle/{vehicleId}")
    public ResponseEntity<Void> unassignDriverFromVehicle(
            @PathVariable Long driverId,
            @PathVariable Long vehicleId) {
        driverVehicleService.unassignDriverFromVehicle(driverId, vehicleId);
        return ResponseEntity.noContent().build();
    }
}