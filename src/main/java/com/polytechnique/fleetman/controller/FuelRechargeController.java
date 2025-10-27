package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.fuelrecharge.FuelRechargeCreateDTO;
import com.polytechnique.fleetman.dto.fuelrecharge.FuelRechargeDTO;
import com.polytechnique.fleetman.service.FuelRechargeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fuel-recharges")
@RequiredArgsConstructor
public class FuelRechargeController {

    private final FuelRechargeService fuelRechargeService;

    @PostMapping
    public ResponseEntity<FuelRechargeDTO> createFuelRecharge(@Valid @RequestBody FuelRechargeCreateDTO fuelRechargeCreateDTO) {
        FuelRechargeDTO createdFuelRecharge = fuelRechargeService.createFuelRecharge(fuelRechargeCreateDTO);
        return new ResponseEntity<>(createdFuelRecharge, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FuelRechargeDTO> getFuelRechargeById(@PathVariable Long id) {
        FuelRechargeDTO fuelRecharge = fuelRechargeService.getFuelRechargeById(id);
        return ResponseEntity.ok(fuelRecharge);
    }

    @GetMapping
    public ResponseEntity<List<FuelRechargeDTO>> getAllFuelRecharges() {
        List<FuelRechargeDTO> fuelRecharges = fuelRechargeService.getAllFuelRecharges();
        return ResponseEntity.ok(fuelRecharges);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<FuelRechargeDTO>> getFuelRechargesByVehicleId(@PathVariable Long vehicleId) {
        List<FuelRechargeDTO> fuelRecharges = fuelRechargeService.getFuelRechargesByVehicleId(vehicleId);
        return ResponseEntity.ok(fuelRecharges);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelRecharge(@PathVariable Long id) {
        fuelRechargeService.deleteFuelRecharge(id);
        return ResponseEntity.noContent().build();
    }
}