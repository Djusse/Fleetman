package com.polytechnique.fleetman.service;

import com.polytechnique.fleetman.dto.position.PositionCreateDTO;
import com.polytechnique.fleetman.dto.position.PositionDTO;
import com.polytechnique.fleetman.entity.PositionEntity;
import com.polytechnique.fleetman.entity.VehicleEntity;
import com.polytechnique.fleetman.exception.ResourceNotFoundException;
import com.polytechnique.fleetman.repository.PositionRepository;
import com.polytechnique.fleetman.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.operation.distance.DistanceOp;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;
    private final VehicleRepository vehicleRepository;
    private static final GeometryFactory geometryFactory = new GeometryFactory();
    private static final double DISTANCE_THRESHOLD = 0.00009; // 10 mètres
    private static final double INCERTITUDE = 0.00009; // ±10 mètres

    /**
     * Enregistre une nouvelle position seulement si elle n'est pas alignée
     * avec les deux dernières positions
     */
    @Transactional
    public PositionDTO savePositionIfNotAligned(PositionCreateDTO dto) {
        // Récupérer les 2 dernières positions du véhicule/conducteur
        List<PositionEntity> lastTwoPositions = positionRepository
                .findLastTwoPositions(dto.getVehicleId());

        // Si moins de 2 positions, enregistrer directement
        if (lastTwoPositions.size() < 2) {
            return createPosition(dto);
        }

        Point pointB = lastTwoPositions.get(0).getCoordinate(); // Dernière
        // si les deux derniers points sont trop proche ne pas enregistrer
        if(sontProchesAvecDistanceOp(pointB,dto.getCoordinate())){
            return convertToDTO(lastTwoPositions.getFirst());
        }

        // Créer les points à partir des positions
        Point pointA = lastTwoPositions.get(1).getCoordinate(); // Avant-dernière
        Point pointC = dto.getCoordinate(); // Nouvelle position

        // Vérifier l'alignement
        if (existeDroite(pointA, pointB, pointC)) {
            // Les 3 points sont alignés, ne pas enregistrer
             return convertToDTO(lastTwoPositions.getFirst());
        }

        // Points non alignés, enregistrer la nouvelle position
        return createPosition(dto);
    }


    @Transactional
    public PositionDTO createPosition(PositionCreateDTO positionCreateDTO) {
        VehicleEntity vehicle = vehicleRepository.findById(positionCreateDTO.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé"));

        PositionEntity position = new PositionEntity();
        position.setCoordinate(positionCreateDTO.getCoordinate());
        position.setPositionDateTime(LocalDateTime.now());
        position.setVehicle(vehicle);

        PositionEntity saved = positionRepository.save(position);
        return convertToDTO(saved);
    }

    @Transactional(readOnly = true)
    public PositionDTO getPositionById(Long positionId) {
        PositionEntity position = positionRepository.findById(positionId)
                .orElseThrow(() -> new ResourceNotFoundException("Position non trouvée"));
        return convertToDTO(position);
    }

    @Transactional(readOnly = true)
    public List<PositionDTO> getAllPositions() {
        return positionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PositionDTO> getPositionsByVehicleId(Long vehicleId) {

        // vérifions si le vehicule existe
        VehicleEntity vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule non trouvé"));

        return positionRepository.findByVehicle_VehicleId(vehicleId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PositionDTO getLatestPositionByVehicleId(Long vehicleId) {
        PositionEntity position = positionRepository.findLatestPositionByVehicle(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Aucune position trouvée pour ce véhicule"));
        return convertToDTO(position);
    }

    @Transactional
    public void deletePosition(Long positionId) {
        if (!positionRepository.existsById(positionId)) {
            throw new ResourceNotFoundException("Position non trouvée");
        }
        positionRepository.deleteById(positionId);
    }

    // fonction qui vérifie si deux points sont distants de moins de DISTANCE_THRESHOLD mètres
    public static boolean sontProchesAvecDistanceOp(Point point1, Point point2) {
        double distance = DistanceOp.distance(point1, point2);
        return distance <= DISTANCE_THRESHOLD;
    }

    // fonction pour dire si les points A,B et C sont presque alignés
    public static boolean existeDroite(Point A, Point B, Point C) {
        // Test de rejet hyper-rapide (95% des cas)
        if (testRejetTresRapide(A, B, C)) {
            return false;
        }

        // Pour les 5% restants, méthode précise
        return existeDroitePassantParZones(A, B, C);
    }

    private static boolean testRejetTresRapide(Point pointA, Point pointB, Point pointC) {
        LineSegment segment = new LineSegment(
                pointA.getCoordinate(),
                pointB.getCoordinate()
        );
        return segment.distance(pointC.getCoordinate()) > (INCERTITUDE * 3.0);
    }

    public static boolean existeDroitePassantParZones(Point pointA, Point pointB, Point pointC) {
        Geometry zoneA = pointA.buffer(INCERTITUDE);
        Geometry zoneB = pointB.buffer(INCERTITUDE);
        Geometry zoneC = pointC.buffer(INCERTITUDE);

        Geometry enveloppeAB = geometryFactory.createGeometryCollection(
                new Geometry[]{zoneA, zoneB}
        ).convexHull();

        return enveloppeAB.intersects(zoneC);
    }


    private PositionDTO convertToDTO(PositionEntity position) {
        PositionDTO dto = new PositionDTO();
        dto.setPositionId(position.getPositionId());
        dto.setCoordinate(position.getCoordinate());
        dto.setPositionDateTime(position.getPositionDateTime());
        dto.setVehicleId(position.getVehicle().getVehicleId());
        dto.setVehicleName(position.getVehicle().getVehicleName());
        return dto;
    }
}