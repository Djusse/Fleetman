package com.polytechnique.fleetman.service;

import com.polytechnique.fleetman.dto.drivervehicle.DriverVehicleCreateDTO;
import com.polytechnique.fleetman.dto.drivervehicle.DriverVehicleDTO;
import com.polytechnique.fleetman.entity.DriverEntity;
import com.polytechnique.fleetman.entity.DriverVehicleEntity;
import com.polytechnique.fleetman.entity.DriverVehicleId;
import com.polytechnique.fleetman.entity.VehicleEntity;
import com.polytechnique.fleetman.repository.DriverRepository;
import com.polytechnique.fleetman.repository.DriverVehicleRepository;
import com.polytechnique.fleetman.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverVehicleService {

    private final DriverVehicleRepository driverVehicleRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public DriverVehicleDTO assignDriverToVehicle(DriverVehicleCreateDTO driverVehicleCreateDTO) {
        // Vérifier que le conducteur existe
        DriverEntity driver = driverRepository.findById(driverVehicleCreateDTO.getDriverId())
                .orElseThrow(() -> new RuntimeException("Conducteur non trouvé"));

        // Vérifier que le véhicule existe
        VehicleEntity vehicle = vehicleRepository.findById(driverVehicleCreateDTO.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));

        // Vérifier si l'assignation existe déjà
        if (driverVehicleRepository.existsByDriver_DriverIdAndVehicle_VehicleId(
                driverVehicleCreateDTO.getDriverId(),
                driverVehicleCreateDTO.getVehicleId())) {
            throw new RuntimeException("Ce conducteur est déjà assigné à ce véhicule");
        }

        // Créer l'assignation
        DriverVehicleEntity driverVehicle = new DriverVehicleEntity();
        driverVehicle.setDriver(driver);
        driverVehicle.setVehicle(vehicle);

        DriverVehicleEntity saved = driverVehicleRepository.save(driverVehicle);

        // FORCER LE RECHARGEMENT pour obtenir assignedAt
        driverVehicleRepository.flush();

        // Recharger l'entité depuis la base de données
        DriverVehicleId id = new DriverVehicleId(saved.getDriver().getDriverId(), saved.getVehicle().getVehicleId());
        DriverVehicleEntity reloaded = driverVehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Erreur lors du rechargement"));

        return convertToDTO(reloaded);
    }

    @Transactional(readOnly = true)
    public List<DriverVehicleDTO> getVehiclesByDriverId(Long driverId) {
        return driverVehicleRepository.findByDriver_DriverId(driverId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DriverVehicleDTO> getDriversByVehicleId(Long vehicleId) {
        return driverVehicleRepository.findByVehicle_VehicleId(vehicleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DriverVehicleDTO> getAllAssignments() {
        return driverVehicleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void unassignDriverFromVehicle(Long driverId, Long vehicleId) {
        DriverEntity driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Conducteur non trouvé"));
        VehicleEntity vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));

        DriverVehicleEntity driverVehicle = driverVehicleRepository
                .findByDriverAndVehicle(driver, vehicle)
                .orElseThrow(() -> new RuntimeException("Assignation non trouvée"));

        driverVehicleRepository.delete(driverVehicle);
    }

    private DriverVehicleDTO convertToDTO(DriverVehicleEntity driverVehicle) {
        return new DriverVehicleDTO(
                driverVehicle.getDriver().getDriverId(),
                driverVehicle.getDriver().getDriverName(),
                driverVehicle.getVehicle().getVehicleId(),
                driverVehicle.getVehicle().getVehicleName(),
                driverVehicle.getVehicle().getVehicleRegistrationNumber(),
                driverVehicle.getAssignedAt()
        );
    }
}