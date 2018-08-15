package uk.co.noxtech.util.jackson;

import java.io.IOException;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.client.model.geojson.GeoJsonObjectType;

public class GeoJsonPointDeserializer extends JsonDeserializer<GeoJsonPoint> {

    private final static String JSON_KEY_GEOJSON_TYPE = "type";
    private final static String JSON_KEY_GEOJSON_COORDS = "coordinates";

    @Override
    public GeoJsonPoint deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        final JsonNode tree = parser.getCodec().readTree(parser);
        final String type = tree.get(JSON_KEY_GEOJSON_TYPE).asText();
        final JsonNode coordsNode = tree.get(JSON_KEY_GEOJSON_COORDS);

        double x;
        double y;
        if (GeoJsonObjectType.POINT.getTypeName().equalsIgnoreCase(type)) {
            x = coordsNode.get(0).asDouble(0);
            y = coordsNode.get(1).asDouble(0);
        } else {
            throw new JsonParseException(parser, "Unable to deserialize GeoJsonPoint.");
        }

        return new GeoJsonPoint(x, y);
    }

}
