package uk.co.noxtech.util.jackson;

import java.io.IOException;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Shamelessly stolen from https://www.alessandrorosa.com/geojson-serialization-issues-with-spring-data-mongodb/
 */
public class GeoJsonPolygonSerializer extends JsonSerializer<GeoJsonPolygon> {

    @Override
    public void serialize(GeoJsonPolygon value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeStringField("type", value.getType());
        generator.writeArrayFieldStart("coordinates");
        for (GeoJsonLineString ls : value.getCoordinates()) {
            generator.writeStartArray();
            for (Point p : ls.getCoordinates()) {
                generator.writeObject(new double[]{p.getX(), p.getY()});
            }
            generator.writeEndArray();
        }
        generator.writeEndArray();
        generator.writeEndObject();
    }

}
