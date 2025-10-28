package com.polytechnique.fleetman.dto.notification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO pour la création d'une notification")
public class NotificationCreateDTO {

    @NotBlank(message = "Le sujet est obligatoire")
    @Size(max = 200, message = "Le sujet ne doit pas dépasser 200 caractères")
    @Schema(
            description = "Sujet ou titre de la notification",
            example = "Maintenance programmée",
            requiredMode = Schema.RequiredMode.REQUIRED,
            maxLength = 200
    )
    private String notificationSubject;

    @NotBlank(message = "Le contenu est obligatoire")
    @Schema(
            description = "Contenu détaillé de la notification",
            example = "Votre véhicule Renault Trafic est programmé pour une maintenance le 20/01/2024",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String notificationContent;

    @NotNull(message = "L'ID utilisateur est obligatoire")
    @Schema(
            description = "ID de l'utilisateur destinataire",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long userId;
}