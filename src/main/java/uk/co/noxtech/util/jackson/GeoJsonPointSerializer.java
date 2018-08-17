package uk.co.noxtech.util.jackson;

import java.io.IOException;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Shamelessly stolen from https://www.alessandrorosa.com/geojson-serialization-issues-with-spring-data-mongodb/
 */
public class GeoJsonPointSerializer extends JsonSerializer<GeoJsonPoint> {

    @Override
    public void serialize(GeoJsonPoint value, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeStringField("type", value.getType());
        generator.writeArrayFieldStart("coordinates");
        generator.writeObject(value.getCoordinates());
        generator.writeEndArray();
        generator.writeEndObject();
    }

}
