package uk.co.noxtech.util.jackson;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

public class GeoJsonPolygonSerializerTest extends AbstractGeoJsonSerializerTest {

    private GeoJsonPolygonSerializer underTest;

    @Before
    public void setUp() {
        underTest = new GeoJsonPolygonSerializer();
    }

    @Test
    public void serialize_shouldSerializeGeoJsonPolygon_toString() throws Exception {
        GeoJsonPolygon value = new GeoJsonPolygon(
            new Point(0.11, 0.12),
            new Point(0.21, 0.22),
            new Point(0.31, 0.32),
            new Point(0.41, 0.42));

        underTest.serialize(value, generator, serializerProvider);

        generator.flush();
        assertThat(writer.toString()).isEqualTo("{\"type\":\"Polygon\",\"coordinates\":[[[0.11,0.12],[0.21,0.22],[0.31,0.32],[0.41,0.42]]]}");
    }

}
