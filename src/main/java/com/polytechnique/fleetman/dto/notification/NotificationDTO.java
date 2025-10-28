package com.polytechnique.fleetman.dto.notification;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO représentant une notification")
public class NotificationDTO {

    @Schema(description = "ID unique de la notification", example = "1")
    private Long notificationId;

    @Schema(description = "Sujet ou titre de la notification", example = "Maintenance programmée")
    private String notificationSubject;

    @Schema(description = "Contenu détaillé de la notification", example = "Votre véhicule est programmé pour une maintenance")
    private String notificationContent;

    @Schema(description = "Date et heure de création de la notification", example = "2024-01-15T14:30:00")
    private LocalDateTime notificationDateTime;

    @Schema(
            description = "État de lecture de la notification",
            example = "false",
            allowableValues = {"true", "false"}
    )
    private Boolean notificationState;

    @Schema(description = "ID de l'utilisateur destinataire", example = "1")
    private Long userId;
}