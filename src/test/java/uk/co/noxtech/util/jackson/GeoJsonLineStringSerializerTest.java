package uk.co.noxtech.util.jackson;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;

public class GeoJsonLineStringSerializerTest extends AbstractGeoJsonSerializerTest {

    private GeoJsonLineStringSerializer underTest;

    @Before
    public void setUp() {
        underTest = new GeoJsonLineStringSerializer();
    }

    @Test
    public void serialize_shouldSerializeGeoJsonLineString_toString() throws Exception {
        GeoJsonLineString value = new GeoJsonLineString(new Point(0.1, 0.2), new Point(1.0, 2.0));

        underTest.serialize(value, generator, serializerProvider);

        generator.flush();
        assertThat(writer.toString()).isEqualTo("{\"type\":\"LineString\",\"coordinates\":[[0.1,0.2],[1.0,2.0]]}");
    }

}
