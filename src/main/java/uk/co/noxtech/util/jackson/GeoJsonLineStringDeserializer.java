package uk.co.noxtech.util.jackson;

import static com.google.common.collect.Streams.stream;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.client.model.geojson.GeoJsonObjectType;

public class GeoJsonLineStringDeserializer extends JsonDeserializer<GeoJsonLineString> {

    private final static String JSON_KEY_GEOJSON_TYPE = "type";
    private final static String JSON_KEY_GEOJSON_COORDS = "coordinates";

    @Override
    public GeoJsonLineString deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        final JsonNode tree = parser.getCodec().readTree(parser);
        final String type = tree.get(JSON_KEY_GEOJSON_TYPE).asText();
        final JsonNode coordsNode = tree.get(JSON_KEY_GEOJSON_COORDS);

        if (GeoJsonObjectType.LINE_STRING.getTypeName().equalsIgnoreCase(type)) {
            if (coordsNode.size() == 2) {
                return new GeoJsonLineString(
                    stream(coordsNode.elements())
                        .map(this::toPoint)
                        .collect(Collectors.toList()));
            } else {
                throw new JsonParseException(parser, "Unable to deserialize GeoJsonLineString, need only 2 points.");
            }
        }
        throw new JsonParseException(parser, "Unable to deserialize GeoJsonLineString.");
    }

    private Point toPoint(JsonNode node) {
        if (node.isArray()) {
            return new Point(node.get(0).asDouble(), node.get(1).asDouble());
        }
        return null;
    }

}
