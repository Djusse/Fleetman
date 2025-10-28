package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.vehicle.VehicleCreateDTO;
import com.polytechnique.fleetman.dto.vehicle.VehicleDTO;
import com.polytechnique.fleetman.dto.vehicle.VehicleUpdateDTO;
import com.polytechnique.fleetman.service.VehicleService;
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
@RequestMapping("/vehicles")
@RequiredArgsConstructor
@Tag(name = "Véhicules", description = "API de gestion des véhicules de la flotte")
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping
    @Operation(
            summary = "Créer un nouveau véhicule",
            description = "Enregistre un nouveau véhicule dans la flotte avec toutes ses caractéristiques techniques"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Véhicule créé avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VehicleDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides (validation échouée)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Un véhicule avec ce numéro d'immatriculation existe déjà",
                    content = @Content
            )
    })
    public ResponseEntity<VehicleDTO> createVehicle(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Informations du véhicule à créer",
                    required = true
            )
            VehicleCreateDTO vehicleCreateDTO) {
        VehicleDTO createdVehicle = vehicleService.createVehicle(vehicleCreateDTO);
        return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
    }

    @GetMapping("/{vehicleId}")
    @Operation(
            summary = "Récupérer un véhicule par son ID",
            description = "Retourne les détails complets d'un véhicule spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Véhicule trouvé",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VehicleDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Véhicule non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<VehicleDTO> getVehicleById(
            @Parameter(description = "ID du véhicule", required = true, example = "1")
            @PathVariable Long vehicleId) {
        VehicleDTO vehicle = vehicleService.getVehicleById(vehicleId);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping("/registration/{registrationNumber}")
    @Operation(
            summary = "Récupérer un véhicule par son numéro d'immatriculation",
            description = "Retourne les détails d'un véhicule en utilisant son numéro d'immatriculation unique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Véhicule trouvé",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VehicleDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Véhicule non trouvé avec ce numéro d'immatriculation",
                    content = @Content
            )
    })
    public ResponseEntity<VehicleDTO> getVehicleByRegistrationNumber(
            @Parameter(description = "Numéro d'immatriculation du véhicule", required = true, example = "AB-123-CD")
            @PathVariable String registrationNumber) {
        VehicleDTO vehicle = vehicleService.getVehicleByRegistrationNumber(registrationNumber);
        return ResponseEntity.ok(vehicle);
    }

    @GetMapping
    @Operation(
            summary = "Lister tous les véhicules",
            description = "Retourne la liste complète de tous les véhicules enregistrés dans la flotte"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des véhicules récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VehicleDTO.class))
                    )
            )
    })
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        List<VehicleDTO> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Récupérer les véhicules d'un utilisateur",
            description = "Retourne tous les véhicules associés à un utilisateur spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des véhicules de l'utilisateur récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VehicleDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Utilisateur non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<List<VehicleDTO>> getVehiclesByUserId(
            @Parameter(description = "ID de l'utilisateur", required = true, example = "1")
            @PathVariable Long userId) {
        List<VehicleDTO> vehicles = vehicleService.getVehiclesByUserId(userId);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/type/{vehicleType}")
    @Operation(
            summary = "Récupérer les véhicules par type",
            description = "Retourne tous les véhicules d'un type spécifique (camion, voiture, van, etc.)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des véhicules du type spécifié récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VehicleDTO.class))
                    )
            )
    })
    public ResponseEntity<List<VehicleDTO>> getVehiclesByType(
            @Parameter(description = "Type de véhicule", required = true, example = "Camion")
            @PathVariable String vehicleType) {
        List<VehicleDTO> vehicles = vehicleService.getVehiclesByType(vehicleType);
        return ResponseEntity.ok(vehicles);
    }

    @PutMapping("/{vehicleId}")
    @Operation(
            summary = "Mettre à jour un véhicule",
            description = "Met à jour les informations d'un véhicule existant. Seuls les champs fournis seront mis à jour."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Véhicule mis à jour avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = VehicleDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides (validation échouée)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Véhicule non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<VehicleDTO> updateVehicle(
            @Parameter(description = "ID du véhicule à modifier", required = true, example = "1")
            @PathVariable Long vehicleId,
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nouvelles informations du véhicule (seuls les champs fournis seront mis à jour)",
                    required = true
            )
            VehicleUpdateDTO vehicleUpdateDTO) {
        VehicleDTO updatedVehicle = vehicleService.updateVehicle(vehicleId, vehicleUpdateDTO);
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/{vehicleId}")
    @Operation(
            summary = "Supprimer un véhicule",
            description = "Supprime définitivement un véhicule de la flotte"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Véhicule supprimé avec succès",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Véhicule non trouvé",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Impossible de supprimer - le véhicule est actuellement en service",
                    content = @Content
            )
    })
    public ResponseEntity<Void> deleteVehicle(
            @Parameter(description = "ID du véhicule à supprimer", required = true, example = "1")
            @PathVariable Long vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
        return ResponseEntity.noContent().build();
    }
}