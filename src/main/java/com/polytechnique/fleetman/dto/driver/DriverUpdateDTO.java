package com.polytechnique.fleetman.dto.driver;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverUpdateDTO {

    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String driverName;

    @Email(message = "Format d'email invalide")
    @Size(max = 150, message = "L'email ne doit pas dépasser 150 caractères")
    private String driverEmail;

    @Size(max = 20, message = "Le numéro ne doit pas dépasser 20 caractères")
    private String driverPhoneNumber;

    @Size(max = 100, message = "Le nom du contact d'urgence ne doit pas dépasser 100 caractères")
    private String emergencyContactName;

    @Size(max = 20, message = "Le contact d'urgence ne doit pas dépasser 20 caractères")
    private String emergencyContact;

    private String personnalInformations;
}
