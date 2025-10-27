package com.polytechnique.fleetman.custom;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.locationtech.jts.geom.Geometry;
import org.ugeojson.builder.UltimateGeoJSONBuilder;
import org.ugeojson.model.geometry.GeometryDto;
import org.ugeojson.parser.util.JtsUGeojsonConversionUtil;

import java.io.IOException;

public class CustomUGeojsonSerializer extends JsonSerializer<Geometry> {
    @Override
    public void serialize(Geometry geometry, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        JtsUGeojsonConversionUtil jtsUGeojsonConversionUtil = new JtsUGeojsonConversionUtil();
        GeometryDto geometryDto = jtsUGeojsonConversionUtil.toGeometryDto(geometry);
        String geoJSON = UltimateGeoJSONBuilder.getInstance().toGeoJSON(geometryDto);

        gen.writeRawValue(geoJSON);
    }
}
