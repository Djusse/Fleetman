package com.polytechnique.fleetman.service;

import com.polytechnique.fleetman.dto.vehicle.VehicleCreateDTO;
import com.polytechnique.fleetman.dto.vehicle.VehicleDTO;
import com.polytechnique.fleetman.dto.vehicle.VehicleUpdateDTO;
import com.polytechnique.fleetman.entity.UserEntity;
import com.polytechnique.fleetman.entity.VehicleEntity;
import com.polytechnique.fleetman.repository.UserRepository;
import com.polytechnique.fleetman.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    @Transactional
    public VehicleDTO createVehicle(VehicleCreateDTO vehicleCreateDTO) {
        if (vehicleRepository.existsByVehicleRegistrationNumber(vehicleCreateDTO.getVehicleRegistrationNumber())) {
            throw new RuntimeException("Numéro d'immatriculation déjà utilisé");
        }

        UserEntity user = userRepository.findById(vehicleCreateDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        VehicleEntity vehicle = new VehicleEntity();
        vehicle.setVehicleMake(vehicleCreateDTO.getVehicleMake());
        vehicle.setVehicleName(vehicleCreateDTO.getVehicleName());
        vehicle.setVehicleRegistrationNumber(vehicleCreateDTO.getVehicleRegistrationNumber());
        vehicle.setVehicleType(vehicleCreateDTO.getVehicleType());
        vehicle.setVehicleImage(vehicleCreateDTO.getVehicleImage());
        vehicle.setVehicleDocument(vehicleCreateDTO.getVehicleDocument());
        vehicle.setVehicleIotAddress(vehicleCreateDTO.getVehicleIotAddress());
        vehicle.setVehicleFuelLevel(vehicleCreateDTO.getVehicleFuelLevel());
        vehicle.setVehicleNumberPassengers(vehicleCreateDTO.getVehicleNumberPassengers());
        vehicle.setVehicleSpeed(vehicleCreateDTO.getVehicleSpeed());
        vehicle.setUser(user);

        VehicleEntity savedVehicle = vehicleRepository.save(vehicle);
        return convertToDTO(savedVehicle);
    }

    @Transactional(readOnly = true)
    public VehicleDTO getVehicleById(Long vehicleId) {
        VehicleEntity vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));
        return convertToDTO(vehicle);
    }

    @Transactional(readOnly = true)
    public VehicleDTO getVehicleByRegistrationNumber(String registrationNumber) {
        VehicleEntity vehicle = vehicleRepository.findByVehicleRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));
        return convertToDTO(vehicle);
    }

    @Transactional(readOnly = true)
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VehicleDTO> getVehiclesByUserId(Long userId) {
        return vehicleRepository.findByUser_UserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VehicleDTO> getVehiclesByType(String vehicleType) {
        return vehicleRepository.findByVehicleType(vehicleType).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public VehicleDTO updateVehicle(Long vehicleId, VehicleUpdateDTO vehicleUpdateDTO) {
        VehicleEntity vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));

        if (vehicleUpdateDTO.getVehicleMake() != null) {
            vehicle.setVehicleMake(vehicleUpdateDTO.getVehicleMake());
        }
        if (vehicleUpdateDTO.getVehicleName() != null) {
            vehicle.setVehicleName(vehicleUpdateDTO.getVehicleName());
        }
        if (vehicleUpdateDTO.getVehicleType() != null) {
            vehicle.setVehicleType(vehicleUpdateDTO.getVehicleType());
        }
        if (vehicleUpdateDTO.getVehicleImage() != null) {
            vehicle.setVehicleImage(vehicleUpdateDTO.getVehicleImage());
        }
        if (vehicleUpdateDTO.getVehicleDocument() != null) {
            vehicle.setVehicleDocument(vehicleUpdateDTO.getVehicleDocument());
        }
        if (vehicleUpdateDTO.getVehicleIotAddress() != null) {
            vehicle.setVehicleIotAddress(vehicleUpdateDTO.getVehicleIotAddress());
        }
        if (vehicleUpdateDTO.getVehicleFuelLevel() != null) {
            vehicle.setVehicleFuelLevel(vehicleUpdateDTO.getVehicleFuelLevel());
        }
        if (vehicleUpdateDTO.getVehicleNumberPassengers() != null) {
            vehicle.setVehicleNumberPassengers(vehicleUpdateDTO.getVehicleNumberPassengers());
        }
        if (vehicleUpdateDTO.getVehicleSpeed() != null) {
            vehicle.setVehicleSpeed(vehicleUpdateDTO.getVehicleSpeed());
        }

        VehicleEntity updatedVehicle = vehicleRepository.save(vehicle);
        return convertToDTO(updatedVehicle);
    }

    @Transactional
    public void deleteVehicle(Long vehicleId) {
        if (!vehicleRepository.existsById(vehicleId)) {
            throw new RuntimeException("Véhicule non trouvé");
        }
        vehicleRepository.deleteById(vehicleId);
    }

    private VehicleDTO convertToDTO(VehicleEntity vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setVehicleId(vehicle.getVehicleId());
        dto.setVehicleMake(vehicle.getVehicleMake());
        dto.setVehicleName(vehicle.getVehicleName());
        dto.setVehicleRegistrationNumber(vehicle.getVehicleRegistrationNumber());
        dto.setVehicleType(vehicle.getVehicleType());
        dto.setVehicleImage(vehicle.getVehicleImage());
        dto.setVehicleDocument(vehicle.getVehicleDocument());
        dto.setVehicleIotAddress(vehicle.getVehicleIotAddress());
        dto.setVehicleFuelLevel(vehicle.getVehicleFuelLevel());
        dto.setVehicleNumberPassengers(vehicle.getVehicleNumberPassengers());
        dto.setVehicleSpeed(vehicle.getVehicleSpeed());
        dto.setUserId(vehicle.getUser().getUserId());
        dto.setCreatedAt(vehicle.getCreatedAt());
        dto.setUpdatedAt(vehicle.getUpdatedAt());

        return dto;
    }
}
