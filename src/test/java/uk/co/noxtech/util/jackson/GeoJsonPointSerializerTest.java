package uk.co.noxtech.util.jackson;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public class GeoJsonPointSerializerTest extends AbstractGeoJsonSerializerTest {

    private GeoJsonPointSerializer underTest;

    @Before
    public void setUp() {
        underTest = new GeoJsonPointSerializer();
    }

    @Test
    public void serialize_shouldSerializeGeoJsonPoint_toString() throws Exception {
        GeoJsonPoint value = new GeoJsonPoint(new Point(0.1, 0.2));

        underTest.serialize(value, generator, serializerProvider);

        generator.flush();
        assertThat(writer.toString()).isEqualTo("{\"type\":\"Point\",\"coordinates\":[[0.1,0.2]]}");
    }

}
