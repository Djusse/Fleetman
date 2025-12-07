package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.position.PositionCreateDTO;
import com.polytechnique.fleetman.dto.position.PositionDTO;
import com.polytechnique.fleetman.service.PositionService;
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
@RequestMapping("/positions")
@RequiredArgsConstructor
@Tag(name = "Positions GPS", description = "API de suivi géolocalisation en temps réel des véhicules")
public class PositionController {

    private final PositionService positionService;

    @PostMapping
    @Operation(
            summary = "Enregistrer une nouvelle position GPS",
            description = """
            Crée un nouvel enregistrement de position GPS pour un véhicule.
            **Géolocalisation** : Les coordonnées GPS sont obligatoires.
            **Horodatage** : La date et heure sont automatiquement enregistrées.
            **Véhicule** : Chaque position doit être associée à un véhicule.
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Position GPS enregistrée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PositionDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides - coordonnées ou véhicule manquant",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Véhicule non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<PositionDTO> createPosition(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Données de position GPS à enregistrer",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PositionCreateDTO.class))
            )
            PositionCreateDTO positionCreateDTO) {
        PositionDTO createdPosition = positionService.savePositionIfNotAligned(positionCreateDTO);
        return new ResponseEntity<>(createdPosition, HttpStatus.CREATED);
    }

    @GetMapping("/{positionId}")
    @Operation(
            summary = "Récupérer une position par son ID",
            description = "Retourne les détails complets d'un enregistrement de position spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Position trouvée",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PositionDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Position non trouvée",
                    content = @Content
            )
    })
    public ResponseEntity<PositionDTO> getPositionById(
            @Parameter(
                    description = "ID de l'enregistrement de position",
                    required = true,
                    example = "1"
            )
            @PathVariable Long positionId) {
        PositionDTO position = positionService.getPositionById(positionId);
        return ResponseEntity.ok(position);
    }

    @GetMapping
    @Operation(
            summary = "Lister toutes les positions enregistrées",
            description = "Retourne l'historique complet de toutes les positions GPS enregistrées dans le système"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Liste des positions récupérée avec succès",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PositionDTO.class))
            )
    )
    public ResponseEntity<List<PositionDTO>> getAllPositions() {
        List<PositionDTO> positions = positionService.getAllPositions();
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/vehicle/{vehicleId}")
    @Operation(
            summary = "Obtenir l'historique des positions d'un véhicule",
            description = "Retourne l'historique complet des positions GPS pour un véhicule spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Historique des positions récupéré avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PositionDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Véhicule non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<List<PositionDTO>> getPositionsByVehicleId(
            @Parameter(
                    description = "ID du véhicule",
                    required = true,
                    example = "1"
            )
            @PathVariable Long vehicleId) {
        List<PositionDTO> positions = positionService.getPositionsByVehicleId(vehicleId);
        return ResponseEntity.ok(positions);
    }

    @GetMapping("/vehicle/{vehicleId}/latest")
    @Operation(
            summary = "Obtenir la dernière position d'un véhicule",
            description = "Retourne la position GPS la plus récente enregistrée pour un véhicule spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Dernière position récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PositionDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Aucune position trouvée pour ce véhicule",
                    content = @Content
            )
    })
    public ResponseEntity<PositionDTO> getLatestPositionByVehicleId(
            @Parameter(
                    description = "ID du véhicule",
                    required = true,
                    example = "1"
            )
            @PathVariable Long vehicleId) {
        PositionDTO position = positionService.getLatestPositionByVehicleId(vehicleId);
        return ResponseEntity.ok(position);
    }

    @DeleteMapping("/{positionId}")
    @Operation(
            summary = "Supprimer un enregistrement de position",
            description = "Supprime définitivement un enregistrement de position GPS du système"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Position supprimée avec succès",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Position non trouvée",
                    content = @Content
            )
    })
    public ResponseEntity<Void> deletePosition(
            @Parameter(
                    description = "ID de la position à supprimer",
                    required = true,
                    example = "1"
            )
            @PathVariable Long positionId) {
        positionService.deletePosition(positionId);
        return ResponseEntity.noContent().build();
    }
}