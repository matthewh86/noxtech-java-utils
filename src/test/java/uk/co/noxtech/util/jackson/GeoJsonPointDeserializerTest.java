package uk.co.noxtech.util.jackson;

import static java.lang.ClassLoader.getSystemResourceAsStream;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public class GeoJsonPointDeserializerTest extends AbstractGeoJsonDeserializerTest {

    private GeoJsonPointDeserializer underTest;

    @Before
    public void setUp() throws Exception {
        parser = objectMapper.getFactory().createParser(getSystemResourceAsStream("jsonPoint.json"));

        underTest = new GeoJsonPointDeserializer();
    }

    @Test
    public void deserialize_shouldDeserializeGeoJsonPoint_fromJson() throws Exception {
        GeoJsonPoint result = underTest.deserialize(parser, context);

        assertThat(result).isEqualTo(new GeoJsonPoint(0.1, 0.2));
    }

}
