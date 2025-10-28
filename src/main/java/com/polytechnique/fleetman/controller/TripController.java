package com.polytechnique.fleetman.controller;

import com.polytechnique.fleetman.dto.trip.TripCreateDTO;
import com.polytechnique.fleetman.dto.trip.TripDTO;
import com.polytechnique.fleetman.dto.trip.TripUpdateDTO;
import com.polytechnique.fleetman.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
@Tag(name = "Trips", description = "Gestion des trajets (création, consultation, mise à jour, suppression)")
public class TripController {

    private final TripService tripService;

    @Operation(
            summary = "Créer un nouveau trajet",
            description = "Crée un nouveau trajet à partir des informations fournies dans le TripCreateDTO",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Trajet créé avec succès",
                            content = @Content(schema = @Schema(implementation = TripDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Données invalides", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<TripDTO> createTrip(
            @Valid @RequestBody
            @Parameter(description = "Informations nécessaires pour créer un trajet") TripCreateDTO tripCreateDTO) {
        TripDTO createdTrip = tripService.createTrip(tripCreateDTO);
        return new ResponseEntity<>(createdTrip, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Récupérer un trajet par son ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trajet trouvé",
                            content = @Content(schema = @Schema(implementation = TripDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Trajet non trouvé", content = @Content)
            }
    )
    @GetMapping("/{tripId}")
    public ResponseEntity<TripDTO> getTripById(
            @Parameter(description = "Identifiant unique du trajet") @PathVariable Long tripId) {
        TripDTO trip = tripService.getTripById(tripId);
        return ResponseEntity.ok(trip);
    }

    @Operation(
            summary = "Lister tous les trajets",
            responses = @ApiResponse(responseCode = "200", description = "Liste de tous les trajets",
                    content = @Content(schema = @Schema(implementation = TripDTO.class)))
    )
    @GetMapping
    public ResponseEntity<List<TripDTO>> getAllTrips() {
        List<TripDTO> trips = tripService.getAllTrips();
        return ResponseEntity.ok(trips);
    }

    @Operation(
            summary = "Récupérer les trajets d’un véhicule",
            responses = @ApiResponse(responseCode = "200", description = "Liste des trajets du véhicule")
    )
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<TripDTO>> getTripsByVehicleId(
            @Parameter(description = "ID du véhicule concerné") @PathVariable Long vehicleId) {
        List<TripDTO> trips = tripService.getTripsByVehicleId(vehicleId);
        return ResponseEntity.ok(trips);
    }

    @Operation(
            summary = "Récupérer les trajets d’un conducteur",
            responses = @ApiResponse(responseCode = "200", description = "Liste des trajets du conducteur")
    )
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<TripDTO>> getTripsByDriverId(
            @Parameter(description = "ID du conducteur concerné") @PathVariable Long driverId) {
        List<TripDTO> trips = tripService.getTripsByDriverId(driverId);
        return ResponseEntity.ok(trips);
    }

    @Operation(
            summary = "Lister les trajets en cours",
            description = "Retourne tous les trajets qui ne sont pas encore terminés"
    )
    @GetMapping("/ongoing")
    public ResponseEntity<List<TripDTO>> getOngoingTrips() {
        List<TripDTO> trips = tripService.getOngoingTrips();
        return ResponseEntity.ok(trips);
    }

    @Operation(
            summary = "Mettre à jour un trajet",
            description = "Permet de mettre à jour les informations d’un trajet existant",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Trajet mis à jour avec succès"),
                    @ApiResponse(responseCode = "404", description = "Trajet non trouvé", content = @Content)
            }
    )
    @PutMapping("/{tripId}")
    public ResponseEntity<TripDTO> updateTrip(
            @Parameter(description = "Identifiant du trajet à mettre à jour") @PathVariable Long tripId,
            @Valid @RequestBody TripUpdateDTO tripUpdateDTO) {
        TripDTO updatedTrip = tripService.updateTrip(tripId, tripUpdateDTO);
        return ResponseEntity.ok(updatedTrip);
    }

    @Operation(
            summary = "Supprimer un trajet",
            description = "Supprime un trajet en fonction de son identifiant",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Trajet supprimé avec succès"),
                    @ApiResponse(responseCode = "404", description = "Trajet non trouvé", content = @Content)
            }
    )
    @DeleteMapping("/{tripId}")
    public ResponseEntity<Void> deleteTrip(
            @Parameter(description = "Identifiant du trajet à supprimer") @PathVariable Long tripId) {
        tripService.deleteTrip(tripId);
        return ResponseEntity.noContent().build();
    }
}
