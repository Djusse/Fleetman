package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.maintenance.MaintenanceCreateDTO;
import com.polytechnique.fleetman.dto.maintenance.MaintenanceDTO;
import com.polytechnique.fleetman.service.MaintenanceService;
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
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
@Tag(name = "Maintenances", description = "API de gestion des opérations de maintenance des véhicules")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    @PostMapping
    @Operation(
            summary = "Enregistrer une nouvelle opération de maintenance",
            description = """
            Crée un nouvel enregistrement de maintenance pour un véhicule.
            ⏰ **Date/Heure** : La date et heure sont automatiquement enregistrées.
            📍 **Localisation** : La position GPS de la maintenance est optionnelle.
            💰 **Coût** : Le coût doit être positif ou nul.
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Maintenance enregistrée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MaintenanceDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides - validation échouée",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Véhicule non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<MaintenanceDTO> createMaintenance(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Informations de l'opération de maintenance",
                    required = true,
                    content = @Content(schema = @Schema(implementation = MaintenanceCreateDTO.class))
            )
            MaintenanceCreateDTO maintenanceCreateDTO) {
        MaintenanceDTO createdMaintenance = maintenanceService.createMaintenance(maintenanceCreateDTO);
        return new ResponseEntity<>(createdMaintenance, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Récupérer une maintenance par son ID",
            description = "Retourne les détails complets d'une opération de maintenance spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Maintenance trouvée",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MaintenanceDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Maintenance non trouvée",
                    content = @Content
            )
    })
    public ResponseEntity<MaintenanceDTO> getMaintenanceById(
            @Parameter(
                    description = "ID de l'opération de maintenance",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id) {
        MaintenanceDTO maintenance = maintenanceService.getMaintenanceById(id);
        return ResponseEntity.ok(maintenance);
    }

    @GetMapping
    @Operation(
            summary = "Lister toutes les maintenances",
            description = "Retourne la liste complète de toutes les opérations de maintenance enregistrées"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Liste des maintenances récupérée avec succès",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MaintenanceDTO.class))
            )
    )
    public ResponseEntity<List<MaintenanceDTO>> getAllMaintenances() {
        List<MaintenanceDTO> maintenances = maintenanceService.getAllMaintenances();
        return ResponseEntity.ok(maintenances);
    }

    @GetMapping("/vehicle/{vehicleId}")
    @Operation(
            summary = "Obtenir l'historique des maintenances d'un véhicule",
            description = "Retourne l'historique complet des opérations de maintenance pour un véhicule spécifique, trié par date décroissante"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Historique des maintenances récupéré avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MaintenanceDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Véhicule non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<List<MaintenanceDTO>> getMaintenancesByVehicleId(
            @Parameter(
                    description = "ID du véhicule",
                    required = true,
                    example = "1"
            )
            @PathVariable Long vehicleId) {
        List<MaintenanceDTO> maintenances = maintenanceService.getMaintenancesByVehicleId(vehicleId);
        return ResponseEntity.ok(maintenances);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprimer une opération de maintenance",
            description = "Supprime définitivement un enregistrement de maintenance"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Maintenance supprimée avec succès",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Maintenance non trouvée",
                    content = @Content
            )
    })
    public ResponseEntity<Void> deleteMaintenance(
            @Parameter(
                    description = "ID de la maintenance à supprimer",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.noContent().build();
    }
}