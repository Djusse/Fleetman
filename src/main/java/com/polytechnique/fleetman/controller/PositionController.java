package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.position.PositionCreateDTO;
import com.polytechnique.fleetman.dto.position.PositionDTO;
import com.polytechnique.fleetman.service.PositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @PostMapping
    public ResponseEntity<PositionDTO> createPosition(@Valid @RequestBody PositionCreateDTO positionCreateDTO) {
        PositionDTO createdPosition = positionService.createPosition(positionCreateDTO);
        return new ResponseEntity<>(createdPosition, HttpStatus.CREATED);
    }

    @GetMapping("/{positionId}")
    public ResponseEntity<PositionDTO> getPositionById(@PathVariable Long positionId) {
        PositionDTO position = positionService.getPositionById(positionId);
        return ResponseEntity.ok(position);
    }

    @GetMapping
    public ResponseEntity<List<PositionDTO>> getAllPositions() {
        List<PositionDTO> positions = positionService.getAllPositions();
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<PositionDTO>> getPositionsByVehicleId(@PathVariable Long vehicleId) {
        List<PositionDTO> positions = positionService.getPositionsByVehicleId(vehicleId);
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/vehicle/{vehicleId}/latest")
    public ResponseEntity<PositionDTO> getLatestPositionByVehicleId(@PathVariable Long vehicleId) {
        PositionDTO position = positionService.getLatestPositionByVehicleId(vehicleId);
        return ResponseEntity.ok(position);
    }

    @DeleteMapping("/{positionId}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long positionId) {
        positionService.deletePosition(positionId);
        return ResponseEntity.noContent().build();
    }
}