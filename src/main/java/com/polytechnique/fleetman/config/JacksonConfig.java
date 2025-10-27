package com.polytechnique.fleetman.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.polytechnique.fleetman.custom.CustomUGeojsonDeserializer;
import com.polytechnique.fleetman.custom.CustomUGeojsonSerializer;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfig {

    @Bean
    @Primary  // ← Marquer comme bean principal
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Module pour le support Java Time (dates)
        objectMapper.registerModule(new JavaTimeModule());

        // Module pour la sérialisation/désérialisation spatiale
        SimpleModule spatialModule = new SimpleModule();
        spatialModule.addSerializer(Geometry.class, new CustomUGeojsonSerializer());
        spatialModule.addDeserializer(Polygon.class, new CustomUGeojsonDeserializer<>());
        spatialModule.addDeserializer(LineString.class, new CustomUGeojsonDeserializer<>());
        spatialModule.addDeserializer(Point.class, new CustomUGeojsonDeserializer<>());

        objectMapper.registerModule(spatialModule);

        return objectMapper;
    }
}