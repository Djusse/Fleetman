package com.polytechnique.fleetman.service;

import com.polytechnique.fleetman.dto.position.PositionCreateDTO;
import com.polytechnique.fleetman.dto.position.PositionDTO;
import com.polytechnique.fleetman.entity.PositionEntity;
import com.polytechnique.fleetman.entity.VehicleEntity;
import com.polytechnique.fleetman.exception.ResourceNotFoundException;
import com.polytechnique.fleetman.repository.PositionRepository;
import com.polytechnique.fleetman.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public PositionDTO createPosition(PositionCreateDTO positionCreateDTO) {
        VehicleEntity vehicle = vehicleRepository.findById(positionCreateDTO.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé"));

        PositionEntity position = new PositionEntity();
        position.setCoordinate(positionCreateDTO.getCoordinate());
        position.setPositionDateTime(LocalDateTime.now());
        position.setVehicle(vehicle);

        PositionEntity saved = positionRepository.save(position);
        return convertToDTO(saved);
    }

    @Transactional(readOnly = true)
    public PositionDTO getPositionById(Long positionId) {
        PositionEntity position = positionRepository.findById(positionId)
                .orElseThrow(() -> new ResourceNotFoundException("Position non trouvée"));
        return convertToDTO(position);
    }

    @Transactional(readOnly = true)
    public List<PositionDTO> getAllPositions() {
        return positionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PositionDTO> getPositionsByVehicleId(Long vehicleId) {

        // vérifions si le vehicule existe
        VehicleEntity vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé"));

        return positionRepository.findByVehicle_VehicleId(vehicleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PositionDTO getLatestPositionByVehicleId(Long vehicleId) {
        PositionEntity position = positionRepository.findLatestPositionByVehicle(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Aucune position trouvée pour ce véhicule"));
        return convertToDTO(position);
    }

    @Transactional
    public void deletePosition(Long positionId) {
        if (!positionRepository.existsById(positionId)) {
            throw new ResourceNotFoundException("Position non trouvée");
        }
        positionRepository.deleteById(positionId);
    }

    private PositionDTO convertToDTO(PositionEntity position) {
        PositionDTO dto = new PositionDTO();
        dto.setPositionId(position.getPositionId());
        dto.setCoordinate(position.getCoordinate());
        dto.setPositionDateTime(position.getPositionDateTime());
        dto.setVehicleId(position.getVehicle().getVehicleId());
        dto.setVehicleName(position.getVehicle().getVehicleName());
        return dto;
    }
}