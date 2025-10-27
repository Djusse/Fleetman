package com.polytechnique.fleetman.dto.driver;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {
    private Long driverId;
    private String driverName;
    private String driverEmail;
    private String driverPhoneNumber;
    private String emergencyContactName;
    private String emergencyContact;
    private String personnalInformations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
