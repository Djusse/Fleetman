package com.polytechnique.fleetman.dto.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationCreateDTO {

    @NotBlank(message = "Le sujet est obligatoire")
    @Size(max = 200, message = "Le sujet ne doit pas dépasser 200 caractères")
    private String notificationSubject;

    @NotBlank(message = "Le contenu est obligatoire")
    private String notificationContent;

    @NotNull(message = "L'ID utilisateur est obligatoire")
    private Long userId;
}
