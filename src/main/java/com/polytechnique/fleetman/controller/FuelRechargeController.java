package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.fuelrecharge.FuelRechargeCreateDTO;
import com.polytechnique.fleetman.dto.fuelrecharge.FuelRechargeDTO;
import com.polytechnique.fleetman.service.FuelRechargeService;
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
@RequestMapping("/api/fuel-recharges")
@RequiredArgsConstructor
@Tag(name = "Recharges de Carburant", description = "API de gestion des recharges de carburant des véhicules")
public class FuelRechargeController {

    private final FuelRechargeService fuelRechargeService;

    @PostMapping
    @Operation(
            summary = "Enregistrer une nouvelle recharge de carburant",
            description = """
            Crée un nouvel enregistrement de recharge de carburant pour un véhicule.
            ⏰ **Date/Heure** : La date et heure de recharge sont automatiquement enregistrées.
            📍 **Localisation** : La position GPS de la recharge est optionnelle.
            """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Recharge de carburant enregistrée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FuelRechargeDTO.class)
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
    public ResponseEntity<FuelRechargeDTO> createFuelRecharge(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Informations de la recharge de carburant",
                    required = true,
                    content = @Content(schema = @Schema(implementation = FuelRechargeCreateDTO.class))
            )
            FuelRechargeCreateDTO fuelRechargeCreateDTO) {
        FuelRechargeDTO createdFuelRecharge = fuelRechargeService.createFuelRecharge(fuelRechargeCreateDTO);
        return new ResponseEntity<>(createdFuelRecharge, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Récupérer une recharge par son ID",
            description = "Retourne les détails complets d'une recharge de carburant spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Recharge trouvée",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FuelRechargeDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recharge non trouvée",
                    content = @Content
            )
    })
    public ResponseEntity<FuelRechargeDTO> getFuelRechargeById(
            @Parameter(
                    description = "ID de la recharge de carburant",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id) {
        FuelRechargeDTO fuelRecharge = fuelRechargeService.getFuelRechargeById(id);
        return ResponseEntity.ok(fuelRecharge);
    }

    @GetMapping
    @Operation(
            summary = "Lister toutes les recharges de carburant",
            description = "Retourne la liste complète de toutes les recharges de carburant enregistrées"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Liste des recharges récupérée avec succès",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = FuelRechargeDTO.class))
            )
    )
    public ResponseEntity<List<FuelRechargeDTO>> getAllFuelRecharges() {
        List<FuelRechargeDTO> fuelRecharges = fuelRechargeService.getAllFuelRecharges();
        return ResponseEntity.ok(fuelRecharges);
    }

    @GetMapping("/vehicle/{vehicleId}")
    @Operation(
            summary = "Obtenir les recharges d'un véhicule",
            description = "Retourne l'historique des recharges de carburant pour un véhicule spécifique, trié par date décroissante"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Historique des recharges récupéré avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = FuelRechargeDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Véhicule non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<List<FuelRechargeDTO>> getFuelRechargesByVehicleId(
            @Parameter(
                    description = "ID du véhicule",
                    required = true,
                    example = "1"
            )
            @PathVariable Long vehicleId) {
        List<FuelRechargeDTO> fuelRecharges = fuelRechargeService.getFuelRechargesByVehicleId(vehicleId);
        return ResponseEntity.ok(fuelRecharges);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprimer une recharge de carburant",
            description = "Supprime définitivement un enregistrement de recharge de carburant"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Recharge supprimée avec succès",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recharge non trouvée",
                    content = @Content
            )
    })
    public ResponseEntity<Void> deleteFuelRecharge(
            @Parameter(
                    description = "ID de la recharge à supprimer",
                    required = true,
                    example = "1"
            )
            @PathVariable Long id) {
        fuelRechargeService.deleteFuelRecharge(id);
        return ResponseEntity.noContent().build();
    }
}