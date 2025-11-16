package com.polytechnique.fleetman.service;

import com.polytechnique.fleetman.dto.maintenance.MaintenanceCreateDTO;
import com.polytechnique.fleetman.dto.maintenance.MaintenanceDTO;
import com.polytechnique.fleetman.entity.MaintenanceEntity;
import com.polytechnique.fleetman.entity.VehicleEntity;
import com.polytechnique.fleetman.exception.ResourceNotFoundException;
import com.polytechnique.fleetman.repository.MaintenanceRepository;
import com.polytechnique.fleetman.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public MaintenanceDTO createMaintenance(MaintenanceCreateDTO maintenanceCreateDTO) {
        VehicleEntity vehicle = vehicleRepository.findById(maintenanceCreateDTO.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé"));

        MaintenanceEntity maintenance = new MaintenanceEntity();
        maintenance.setMaintenanceSubject(maintenanceCreateDTO.getMaintenanceSubject());
        maintenance.setMaintenanceCost(maintenanceCreateDTO.getMaintenanceCost());
        maintenance.setMaintenanceDateTime(LocalDateTime.now());
        maintenance.setVehicle(vehicle);

        if (maintenanceCreateDTO.getMaintenancePoint() != null ) {
            maintenance.setMaintenancePoint(maintenanceCreateDTO.getMaintenancePoint());
        }

        MaintenanceEntity saved = maintenanceRepository.save(maintenance);
        return convertToDTO(saved);
    }

    @Transactional(readOnly = true)
    public MaintenanceDTO getMaintenanceById(Long maintenanceId) {
        MaintenanceEntity maintenance = maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance non trouvée"));
        return convertToDTO(maintenance);
    }

    @Transactional(readOnly = true)
    public List<MaintenanceDTO> getAllMaintenances() {
        return maintenanceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MaintenanceDTO> getMaintenancesByVehicleId(Long vehicleId) {

        VehicleEntity vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé"));

        return maintenanceRepository.findByVehicle_VehicleIdOrderByMaintenanceDateTimeDesc(vehicleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteMaintenance(Long maintenanceId) {
        if (!maintenanceRepository.existsById(maintenanceId)) {
            throw new ResourceNotFoundException("Maintenance non trouvée");
        }
        maintenanceRepository.deleteById(maintenanceId);
    }

    private MaintenanceDTO convertToDTO(MaintenanceEntity maintenance) {
        MaintenanceDTO dto = new MaintenanceDTO();
        dto.setMaintenanceId(maintenance.getMaintenanceId());
        dto.setMaintenanceSubject(maintenance.getMaintenanceSubject());
        dto.setMaintenanceCost(maintenance.getMaintenanceCost());
        dto.setMaintenanceDateTime(maintenance.getMaintenanceDateTime());
        dto.setVehicleId(maintenance.getVehicle().getVehicleId());
        dto.setVehicleName(maintenance.getVehicle().getVehicleName());

        if (maintenance.getMaintenancePoint() != null) {
            dto.setMaintenancePoint(maintenance.getMaintenancePoint());
        }

        return dto;
    }
}