package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.positionhistory.PositionHistoryDTO;
import com.polytechnique.fleetman.service.PositionHistoryService;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/position-histories")
@RequiredArgsConstructor
public class PositionHistoryController {

    private final PositionHistoryService positionHistoryService;

    @PostMapping("/vehicle/{vehicleId}")
    public ResponseEntity<PositionHistoryDTO> createPositionHistory(
            @PathVariable Long vehicleId,
            @RequestBody Map<String, List<List<Double>>> request) {

        List<List<Double>> coordinatesList = request.get("coordinates");
        if (coordinatesList == null) {
            throw new RuntimeException("La liste des coordonnées est obligatoire");
        }

        List<Coordinate> coordinates = coordinatesList.stream()
                .map(coord -> new Coordinate(coord.get(0), coord.get(1))) // [longitude, latitude]
                .toList();

        PositionHistoryDTO createdHistory = positionHistoryService.createPositionHistory(vehicleId, coordinates);
        return new ResponseEntity<>(createdHistory, HttpStatus.CREATED);
    }

    @GetMapping("/{positionHistoryId}")
    public ResponseEntity<PositionHistoryDTO> getPositionHistoryById(@PathVariable Long positionHistoryId) {
        PositionHistoryDTO positionHistory = positionHistoryService.getPositionHistoryById(positionHistoryId);
        return ResponseEntity.ok(positionHistory);
    }

    @GetMapping
    public ResponseEntity<List<PositionHistoryDTO>> getAllPositionHistories() {
        List<PositionHistoryDTO> positionHistories = positionHistoryService.getAllPositionHistories();
        return ResponseEntity.ok(positionHistories);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<PositionHistoryDTO>> getPositionHistoriesByVehicleId(@PathVariable Long vehicleId) {
        List<PositionHistoryDTO> positionHistories = positionHistoryService.getPositionHistoriesByVehicleId(vehicleId);
        return ResponseEntity.ok(positionHistories);
    }

    @DeleteMapping("/{positionHistoryId}")
    public ResponseEntity<Void> deletePositionHistory(@PathVariable Long positionHistoryId) {
        positionHistoryService.deletePositionHistory(positionHistoryId);
        return ResponseEntity.noContent().build();
    }
}