package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.driver.DriverCreateDTO;
import com.polytechnique.fleetman.dto.driver.DriverDTO;
import com.polytechnique.fleetman.dto.driver.DriverUpdateDTO;
import com.polytechnique.fleetman.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    public ResponseEntity<DriverDTO> createDriver(@Valid @RequestBody DriverCreateDTO driverCreateDTO) {
        DriverDTO createdDriver = driverService.createDriver(driverCreateDTO);
        return new ResponseEntity<>(createdDriver, HttpStatus.CREATED);
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverDTO> getDriverById(@PathVariable Long driverId) {
        DriverDTO driver = driverService.getDriverById(driverId);
        return ResponseEntity.ok(driver);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<DriverDTO> getDriverByEmail(@PathVariable String email) {
        DriverDTO driver = driverService.getDriverByEmail(email);
        return ResponseEntity.ok(driver);
    }

    @GetMapping
    public ResponseEntity<List<DriverDTO>> getAllDrivers() {
        List<DriverDTO> drivers = driverService.getAllDrivers();
        return ResponseEntity.ok(drivers);
    }

    @PutMapping("/{driverId}")
    public ResponseEntity<DriverDTO> updateDriver(
            @PathVariable Long driverId,
            @Valid @RequestBody DriverUpdateDTO driverUpdateDTO) {
        DriverDTO updatedDriver = driverService.updateDriver(driverId, driverUpdateDTO);
        return ResponseEntity.ok(updatedDriver);
    }

    @DeleteMapping("/{driverId}")
    public ResponseEntity<Void> deleteDriver(@PathVariable Long driverId) {
        driverService.deleteDriver(driverId);
        return ResponseEntity.noContent().build();
    }
}