package com.polytechnique.fleetman.service;

import com.polytechnique.fleetman.dto.fuelrecharge.FuelRechargeCreateDTO;
import com.polytechnique.fleetman.dto.fuelrecharge.FuelRechargeDTO;
import com.polytechnique.fleetman.entity.FuelRechargeEntity;
import com.polytechnique.fleetman.entity.VehicleEntity;
import com.polytechnique.fleetman.exception.ResourceNotFoundException;
import com.polytechnique.fleetman.repository.FuelRechargeRepository;
import com.polytechnique.fleetman.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuelRechargeService {

    private final FuelRechargeRepository fuelRechargeRepository;
    private final VehicleRepository vehicleRepository;

    @Transactional
    public FuelRechargeDTO createFuelRecharge(FuelRechargeCreateDTO fuelRechargeCreateDTO) {
        VehicleEntity vehicle = vehicleRepository.findById(fuelRechargeCreateDTO.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé"));

        FuelRechargeEntity fuelRecharge = new FuelRechargeEntity();
        fuelRecharge.setRechargeQuantity(fuelRechargeCreateDTO.getRechargeQuantity());
        fuelRecharge.setRechargePrice(fuelRechargeCreateDTO.getRechargePrice());
        fuelRecharge.setRechargeDateTime(LocalDateTime.now());
        fuelRecharge.setVehicle(vehicle);

        if (fuelRechargeCreateDTO.getRechargePoint() != null) {
            fuelRecharge.setRechargePoint(fuelRechargeCreateDTO.getRechargePoint());
        }

        FuelRechargeEntity saved = fuelRechargeRepository.save(fuelRecharge);
        return convertToDTO(saved);
    }

    @Transactional(readOnly = true)
    public FuelRechargeDTO getFuelRechargeById(Long rechargeId) {
        FuelRechargeEntity fuelRecharge = fuelRechargeRepository.findById(rechargeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recharge non trouvée"));
        return convertToDTO(fuelRecharge);
    }

    @Transactional(readOnly = true)
    public List<FuelRechargeDTO> getAllFuelRecharges() {
        return fuelRechargeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FuelRechargeDTO> getFuelRechargesByVehicleId(Long vehicleId) {

        VehicleEntity vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé"));

        return fuelRechargeRepository.findByVehicle_VehicleIdOrderByRechargeDateTimeDesc(vehicleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteFuelRecharge(Long rechargeId) {
        if (!fuelRechargeRepository.existsById(rechargeId)) {
            throw new ResourceNotFoundException("Recharge non trouvée");
        }
        fuelRechargeRepository.deleteById(rechargeId);
    }

    private FuelRechargeDTO convertToDTO(FuelRechargeEntity fuelRecharge) {
        FuelRechargeDTO dto = new FuelRechargeDTO();
        dto.setRechargeId(fuelRecharge.getRechargeId());
        dto.setRechargeQuantity(fuelRecharge.getRechargeQuantity());
        dto.setRechargePrice(fuelRecharge.getRechargePrice());
        dto.setRechargeDateTime(fuelRecharge.getRechargeDateTime());
        dto.setVehicleId(fuelRecharge.getVehicle().getVehicleId());
        dto.setVehicleName(fuelRecharge.getVehicle().getVehicleName());

        if (fuelRecharge.getRechargePoint() != null) {
            dto.setRechargePoint(fuelRecharge.getRechargePoint());
        }

        return dto;
    }
}