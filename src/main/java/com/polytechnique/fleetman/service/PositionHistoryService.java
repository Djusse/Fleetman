package com.polytechnique.fleetman.service;

import com.polytechnique.fleetman.dto.positionhistory.PositionHistoryDTO;
import com.polytechnique.fleetman.entity.PositionHistoryEntity;
import com.polytechnique.fleetman.entity.VehicleEntity;
import com.polytechnique.fleetman.repository.PositionHistoryRepository;
import com.polytechnique.fleetman.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionHistoryService {

    private final PositionHistoryRepository positionHistoryRepository;
    private final VehicleRepository vehicleRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @Transactional
    public PositionHistoryDTO createPositionHistory(Long vehicleId, List<Coordinate> coordinates) {
        VehicleEntity vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));

        if (coordinates == null || coordinates.size() < 2) {
            throw new RuntimeException("Au moins 2 coordonnées sont nécessaires pour créer un LineString");
        }

        LineString lineString = geometryFactory.createLineString(
                coordinates.toArray(new Coordinate[0])
        );

        PositionHistoryEntity positionHistory = new PositionHistoryEntity();
        positionHistory.setSummaryCoordinate(lineString);
        positionHistory.setPositionDateTime(LocalDateTime.now());
        positionHistory.setVehicle(vehicle);

        PositionHistoryEntity saved = positionHistoryRepository.save(positionHistory);
        return convertToDTO(saved);
    }

    @Transactional(readOnly = true)
    public PositionHistoryDTO getPositionHistoryById(Long positionHistoryId) {
        PositionHistoryEntity positionHistory = positionHistoryRepository.findById(positionHistoryId)
                .orElseThrow(() -> new RuntimeException("Historique de position non trouvé"));
        return convertToDTO(positionHistory);
    }

    @Transactional(readOnly = true)
    public List<PositionHistoryDTO> getAllPositionHistories() {
        return positionHistoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PositionHistoryDTO> getPositionHistoriesByVehicleId(Long vehicleId) {
        return positionHistoryRepository.findByVehicle_VehicleId(vehicleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePositionHistory(Long positionHistoryId) {
        if (!positionHistoryRepository.existsById(positionHistoryId)) {
            throw new RuntimeException("Historique de position non trouvé");
        }
        positionHistoryRepository.deleteById(positionHistoryId);
    }

    private PositionHistoryDTO convertToDTO(PositionHistoryEntity positionHistory) {
        PositionHistoryDTO dto = new PositionHistoryDTO();
        dto.setPositionHistoryId(positionHistory.getPositionHistoryId());
        dto.setPositionDateTime(positionHistory.getPositionDateTime());
        dto.setVehicleId(positionHistory.getVehicle().getVehicleId());
        dto.setVehicleName(positionHistory.getVehicle().getVehicleName());
        dto.setSummaryCoordinate(positionHistory.getSummaryCoordinate());

        return dto;
    }
}