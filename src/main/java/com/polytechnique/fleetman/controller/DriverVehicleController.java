package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.drivervehicle.DriverVehicleCreateDTO;
import com.polytechnique.fleetman.dto.drivervehicle.DriverVehicleDTO;
import com.polytechnique.fleetman.service.DriverVehicleService;
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
@RequestMapping("/driver-vehicle")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Affectations Conducteur-Véhicule", description = "API de gestion des affectations entre conducteurs et véhicules")
public class DriverVehicleController {

    private final DriverVehicleService driverVehicleService;

    @PostMapping
    @Operation(
            summary = "Assigner un conducteur à un véhicule",
            description = "Crée une nouvelle affectation entre un conducteur et un véhicule. Un conducteur peut être assigné à plusieurs véhicules et vice versa."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Affectation créée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DriverVehicleDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides (validation échouée)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conducteur ou véhicule non trouvé",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Cette affectation existe déjà",
                    content = @Content
            )
    })
    public ResponseEntity<DriverVehicleDTO> assignDriverToVehicle(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "IDs du conducteur et du véhicule à associer",
                    required = true
            )
            DriverVehicleCreateDTO driverVehicleCreateDTO) {
        DriverVehicleDTO assignment = driverVehicleService.assignDriverToVehicle(driverVehicleCreateDTO);
        return new ResponseEntity<>(assignment, HttpStatus.CREATED);
    }

    @GetMapping("/driver/{driverId}")
    @Operation(
            summary = "Récupérer les véhicules d'un conducteur",
            description = "Retourne la liste de tous les véhicules assignés à un conducteur spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des véhicules assignés au conducteur récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DriverVehicleDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conducteur non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<List<DriverVehicleDTO>> getVehiclesByDriverId(
            @Parameter(description = "ID du conducteur", required = true, example = "1")
            @PathVariable Long driverId) {
        List<DriverVehicleDTO> vehicles = driverVehicleService.getVehiclesByDriverId(driverId);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/vehicle/{vehicleId}")
    @Operation(
            summary = "Récupérer les conducteurs d'un véhicule",
            description = "Retourne la liste de tous les conducteurs assignés à un véhicule spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des conducteurs assignés au véhicule récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DriverVehicleDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Véhicule non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<List<DriverVehicleDTO>> getDriversByVehicleId(
            @Parameter(description = "ID du véhicule", required = true, example = "1")
            @PathVariable Long vehicleId) {
        List<DriverVehicleDTO> drivers = driverVehicleService.getDriversByVehicleId(vehicleId);
        return ResponseEntity.ok(drivers);
    }

    @GetMapping
    @Operation(
            summary = "Lister toutes les affectations",
            description = "Retourne la liste complète de toutes les affectations conducteur-véhicule dans le système"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des affectations récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DriverVehicleDTO.class))
                    )
            )
    })
    public ResponseEntity<List<DriverVehicleDTO>> getAllAssignments() {
        List<DriverVehicleDTO> assignments = driverVehicleService.getAllAssignments();
        return ResponseEntity.ok(assignments);
    }

    @DeleteMapping("/driver/{driverId}/vehicle/{vehicleId}")
    @Operation(
            summary = "Retirer l'affectation d'un conducteur à un véhicule",
            description = "Supprime l'affectation entre un conducteur et un véhicule spécifiques"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Affectation supprimée avec succès",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Affectation, conducteur ou véhicule non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<Void> unassignDriverFromVehicle(
            @Parameter(description = "ID du conducteur", required = true, example = "1")
            @PathVariable Long driverId,
            @Parameter(description = "ID du véhicule", required = true, example = "1")
            @PathVariable Long vehicleId) {
        driverVehicleService.unassignDriverFromVehicle(driverId, vehicleId);
        return ResponseEntity.noContent().build();
    }
}