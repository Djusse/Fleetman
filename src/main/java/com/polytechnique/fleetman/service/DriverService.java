package com.polytechnique.fleetman.service;

import com.polytechnique.fleetman.dto.driver.DriverCreateDTO;
import com.polytechnique.fleetman.dto.driver.DriverDTO;
import com.polytechnique.fleetman.dto.driver.DriverUpdateDTO;
import com.polytechnique.fleetman.entity.DriverEntity;
import com.polytechnique.fleetman.exception.ResourceAlreadyExistsException;
import com.polytechnique.fleetman.exception.ResourceNotFoundException;
import com.polytechnique.fleetman.repository.DriverRepository;
import com.polytechnique.fleetman.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final UserRepository userRepository;

    @Transactional
    public DriverDTO createDriver(DriverCreateDTO driverCreateDTO) {
        if (driverRepository.existsByDriverEmail(driverCreateDTO.getDriverEmail())) {
            throw new ResourceAlreadyExistsException("Email déjà utilisé");
        }

        DriverEntity driver = new DriverEntity();
        driver.setDriverName(driverCreateDTO.getDriverName());
        driver.setDriverEmail(driverCreateDTO.getDriverEmail());
        driver.setDriverPhoneNumber(driverCreateDTO.getDriverPhoneNumber());
        driver.setEmergencyContactName(driverCreateDTO.getEmergencyContactName());
        driver.setEmergencyContact(driverCreateDTO.getEmergencyContact());
        driver.setPersonalInformations(driverCreateDTO.getPersonalInformations());

        DriverEntity savedDriver = driverRepository.save(driver);
        return convertToDTO(savedDriver);
    }

    @Transactional(readOnly = true)
    public DriverDTO getDriverById(Long driverId) {
        DriverEntity driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Conducteur non trouvé"));
        return convertToDTO(driver);
    }

    @Transactional(readOnly = true)
    public DriverDTO getDriverByEmail(String email) {
        DriverEntity driver = driverRepository.findByDriverEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Conducteur non trouvé"));
        return convertToDTO(driver);
    }

    @Transactional(readOnly = true)
    public List<DriverDTO> getAllDrivers() {
        return driverRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DriverDTO updateDriver(Long driverId, DriverUpdateDTO driverUpdateDTO) {
        DriverEntity driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Conducteur non trouvé"));

        if (driverUpdateDTO.getDriverName() != null) {
            driver.setDriverName(driverUpdateDTO.getDriverName());
        }
        if (driverUpdateDTO.getDriverEmail() != null) {
            if (!driver.getDriverEmail().equals(driverUpdateDTO.getDriverEmail())
                    && driverRepository.existsByDriverEmail(driverUpdateDTO.getDriverEmail())) {
                throw new ResourceAlreadyExistsException("Email déjà utilisé");
            }
            driver.setDriverEmail(driverUpdateDTO.getDriverEmail());
        }
        if (driverUpdateDTO.getDriverPhoneNumber() != null) {
            driver.setDriverPhoneNumber(driverUpdateDTO.getDriverPhoneNumber());
        }
        if (driverUpdateDTO.getEmergencyContactName() != null) {
            driver.setEmergencyContactName(driverUpdateDTO.getEmergencyContactName());
        }
        if (driverUpdateDTO.getEmergencyContact() != null) {
            driver.setEmergencyContact(driverUpdateDTO.getEmergencyContact());
        }
        if (driverUpdateDTO.getPersonalInformations() != null) {
            driver.setPersonalInformations(driverUpdateDTO.getPersonalInformations());
        }

        DriverEntity updatedDriver = driverRepository.save(driver);
        return convertToDTO(updatedDriver);
    }

    @Transactional
    public void deleteDriver(Long driverId) {
        if (!driverRepository.existsById(driverId)) {
            throw new ResourceNotFoundException("Conducteur non trouvé");
        }
        driverRepository.deleteById(driverId);
    }

    private DriverDTO convertToDTO(DriverEntity driver) {
        return new DriverDTO(
                driver.getDriverId(),
                driver.getDriverName(),
                driver.getDriverEmail(),
                driver.getDriverPhoneNumber(),
                driver.getEmergencyContactName(),
                driver.getEmergencyContact(),
                driver.getPersonalInformations(),
                driver.getCreatedAt(),
                driver.getUpdatedAt()
        );
    }
}