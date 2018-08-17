package uk.co.noxtech.util.jackson;

import java.io.IOException;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Shamelessly stolen from https://www.alessandrorosa.com/geojson-serialization-issues-with-spring-data-mongodb/
 */
public class GeoJsonLineStringSerializer extends JsonSerializer<GeoJsonLineString> {

    @Override
    public void serialize(GeoJsonLineString value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeStringField("type", value.getType());
        generator.writeArrayFieldStart("coordinates");
        for (Point p : value.getCoordinates()) {
            generator.writeObject(new double[]{p.getX(), p.getY()});
        }
        generator.writeEndArray();
        generator.writeEndObject();
    }

}
