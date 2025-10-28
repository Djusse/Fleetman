package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.notification.NotificationCreateDTO;
import com.polytechnique.fleetman.dto.notification.NotificationDTO;
import com.polytechnique.fleetman.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Notifications", description = "API de gestion du système de notifications des utilisateurs")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    @Operation(
            summary = "Créer une nouvelle notification",
            description = """
            Crée une nouvelle notification pour un utilisateur.
            ⏰ **Date/Heure** : La date et heure sont automatiquement enregistrées.
            🔔 **État** : Les notifications sont créées avec l'état 'non lu' (false).
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Notification créée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotificationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides - validation échouée",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utilisateur non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<NotificationDTO> createNotification(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Informations de la notification à créer",
                    required = true,
                    content = @Content(schema = @Schema(implementation = NotificationCreateDTO.class))
            )
            NotificationCreateDTO notificationCreateDTO) {
        NotificationDTO createdNotification = notificationService.createNotification(notificationCreateDTO);
        return new ResponseEntity<>(createdNotification, HttpStatus.CREATED);
    }

    @GetMapping("/{notificationId}")
    @Operation(
            summary = "Récupérer une notification par son ID",
            description = "Retourne les détails complets d'une notification spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification trouvée",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotificationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notification non trouvée",
                    content = @Content
            )
    })
    public ResponseEntity<NotificationDTO> getNotificationById(
            @Parameter(
                    description = "ID de la notification",
                    required = true,
                    example = "1"
            )
            @PathVariable Long notificationId) {
        NotificationDTO notification = notificationService.getNotificationById(notificationId);
        return ResponseEntity.ok(notification);
    }

    @GetMapping
    @Operation(
            summary = "Lister toutes les notifications",
            description = "Retourne la liste complète de toutes les notifications du système"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Liste des notifications récupérée avec succès",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = NotificationDTO.class))
            )
    )
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<NotificationDTO> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Obtenir les notifications d'un utilisateur",
            description = "Retourne toutes les notifications (lus et non lus) d'un utilisateur spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Notifications de l'utilisateur récupérées avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NotificationDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utilisateur non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(
            @Parameter(
                    description = "ID de l'utilisateur",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId) {
        List<NotificationDTO> notifications = notificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}/unread")
    @Operation(
            summary = "Obtenir les notifications non lues d'un utilisateur",
            description = "Retourne uniquement les notifications non lues d'un utilisateur spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Notifications non lues récupérées avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = NotificationDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utilisateur non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<List<NotificationDTO>> getUnreadNotificationsByUserId(
            @Parameter(
                    description = "ID de l'utilisateur",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId) {
        List<NotificationDTO> notifications = notificationService.getUnreadNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}/unread/count")
    @Operation(
            summary = "Compter les notifications non lues",
            description = "Retourne le nombre de notifications non lues pour un utilisateur spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Nombre de notifications non lues",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Long.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utilisateur non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<Long> countUnreadNotifications(
            @Parameter(
                    description = "ID de l'utilisateur",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId) {
        long count = notificationService.countUnreadNotifications(userId);
        return ResponseEntity.ok(count);
    }

    @PatchMapping("/{notificationId}/read")
    @Operation(
            summary = "Marquer une notification comme lue",
            description = "Marque une notification spécifique comme lue"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Notification marquée comme lue",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = NotificationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notification non trouvée",
                    content = @Content
            )
    })
    public ResponseEntity<NotificationDTO> markAsRead(
            @Parameter(
                    description = "ID de la notification à marquer comme lue",
                    required = true,
                    example = "1"
            )
            @PathVariable Long notificationId) {
        NotificationDTO notification = notificationService.markAsRead(notificationId);
        return ResponseEntity.ok(notification);
    }

    @PatchMapping("/user/{userId}/read-all")
    @Operation(
            summary = "Marquer toutes les notifications comme lues",
            description = "Marque toutes les notifications non lues d'un utilisateur comme lues"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Toutes les notifications marquées comme lues"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utilisateur non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<Void> markAllAsRead(
            @Parameter(
                    description = "ID de l'utilisateur",
                    required = true,
                    example = "1"
            )
            @PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{notificationId}")
    @Operation(
            summary = "Supprimer une notification",
            description = "Supprime définitivement une notification du système"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Notification supprimée avec succès",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Notification non trouvée",
                    content = @Content
            )
    })
    public ResponseEntity<Void> deleteNotification(
            @Parameter(
                    description = "ID de la notification à supprimer",
                    required = true,
                    example = "1"
            )
            @PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }
}