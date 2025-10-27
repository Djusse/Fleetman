package com.polytechnique.fleetman.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    private String userName;

    @Email(message = "Format d'email invalide")
    @Size(max = 150, message = "L'email ne doit pas dépasser 150 caractères")
    private String userEmail;

    @Size(max = 20, message = "Le numéro ne doit pas dépasser 20 caractères")
    private String userPhoneNumber;
}
