package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.driver.DriverCreateDTO;
import com.polytechnique.fleetman.dto.driver.DriverDTO;
import com.polytechnique.fleetman.dto.driver.DriverUpdateDTO;
import com.polytechnique.fleetman.dto.vehicle.VehicleDTO;
import com.polytechnique.fleetman.service.DriverService;
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
@RequestMapping("/drivers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Conducteurs", description = "API de gestion des conducteurs de la flotte")
public class DriverController {

    private final DriverService driverService;

    @PostMapping
    @Operation(
            summary = "Créer un nouveau conducteur",
            description = "Crée un nouveau conducteur avec les informations fournies. Tous les champs obligatoires doivent être renseignés."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Conducteur créé avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DriverDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides (validation échouée)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Un conducteur avec cet email existe déjà",
                    content = @Content
            )
    })
    public ResponseEntity<DriverDTO> createDriver(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Informations du conducteur à créer",
                    required = true
            )
            DriverCreateDTO driverCreateDTO) {
        DriverDTO createdDriver = driverService.createDriver(driverCreateDTO);
        return new ResponseEntity<>(createdDriver, HttpStatus.CREATED);
    }

    @GetMapping("/{driverId}")
    @Operation(
            summary = "Récupérer un conducteur par son ID",
            description = "Retourne les détails complets d'un conducteur spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Conducteur trouvé",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DriverDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conducteur non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<DriverDTO> getDriverById(
            @Parameter(description = "ID du conducteur", required = true, example = "1")
            @PathVariable Long driverId) {
        DriverDTO driver = driverService.getDriverById(driverId);
        return ResponseEntity.ok(driver);
    }

    @GetMapping("/email/{email}")
    @Operation(
            summary = "Récupérer un conducteur par son email",
            description = "Retourne les détails d'un conducteur en utilisant son adresse email"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Conducteur trouvé",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DriverDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conducteur non trouvé avec cet email",
                    content = @Content
            )
    })
    public ResponseEntity<DriverDTO> getDriverByEmail(
            @Parameter(description = "Adresse email du conducteur", required = true, example = "jean.dupont@example.com")
            @PathVariable String email) {
        DriverDTO driver = driverService.getDriverByEmail(email);
        return ResponseEntity.ok(driver);
    }

    @GetMapping
    @Operation(
            summary = "Lister tous les conducteurs",
            description = "Retourne la liste complète de tous les conducteurs enregistrés dans le système"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des conducteurs récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DriverDTO.class))
                    )
            )
    })
    public ResponseEntity<List<DriverDTO>> getAllDrivers() {
        List<DriverDTO> drivers = driverService.getAllDrivers();
        return ResponseEntity.ok(drivers);
    }

    @PutMapping("/{driverId}")
    @Operation(
            summary = "Mettre à jour un conducteur",
            description = "Met à jour les informations d'un conducteur existant. Seuls les champs fournis seront mis à jour."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Conducteur mis à jour avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DriverDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Données invalides (validation échouée)",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conducteur non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<DriverDTO> updateDriver(
            @Parameter(description = "ID du conducteur à modifier", required = true, example = "1")
            @PathVariable Long driverId,
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nouvelles informations du conducteur (seuls les champs fournis seront mis à jour)",
                    required = true
            )
            DriverUpdateDTO driverUpdateDTO) {
        DriverDTO updatedDriver = driverService.updateDriver(driverId, driverUpdateDTO);
        return ResponseEntity.ok(updatedDriver);
    }

    @DeleteMapping("/{driverId}")
    @Operation(
            summary = "Supprimer un conducteur",
            description = "Supprime définitivement un conducteur du système"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Conducteur supprimé avec succès",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Conducteur non trouvé",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Impossible de supprimer - le conducteur est associé à des véhicules actifs",
                    content = @Content
            )
    })
    public ResponseEntity<Void> deleteDriver(
            @Parameter(description = "ID du conducteur à supprimer", required = true, example = "1")
            @PathVariable Long driverId) {
        driverService.deleteDriver(driverId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Récupérer les conducteurs d'un utilisateur",
            description = "Retourne tous les conducteurs associés à un utilisateur spécifique"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des conducteurs de l'utilisateur récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = VehicleDTO.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "conducteurs non trouvé",
                    content = @Content
            )
    })
    public ResponseEntity<List<DriverDTO>> getVehiclesByUserId(
            @Parameter(description = "ID de l'utilisateur", required = true, example = "1")
            @PathVariable Long userId) {
        List<DriverDTO> vehicles = driverService.getDriverByUserId(userId);
        return ResponseEntity.ok(vehicles);
    }
}