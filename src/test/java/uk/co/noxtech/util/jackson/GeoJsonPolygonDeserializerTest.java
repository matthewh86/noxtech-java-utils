package uk.co.noxtech.util.jackson;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.noxtech.util.Resource.getResource;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon;

public class GeoJsonPolygonDeserializerTest extends AbstractGeoJsonDeserializerTest {

    private GeoJsonPolygonDeserializer underTest;

    @Before
    public void setUp() throws Exception {
        parser = objectMapper.getFactory().createParser(getResource("jsonPolygon.json"));

        underTest = new GeoJsonPolygonDeserializer();
    }

    @Test
    public void deserialize_shouldDeserializeGeoJsonPolygon_fromJson() throws IOException {
        GeoJsonPolygon result = underTest.deserialize(parser, context);

        assertThat(result).isEqualTo(new GeoJsonPolygon(
                new GeoJsonPoint(0.11, 0.12),
                new GeoJsonPoint(0.21, 0.22),
                new GeoJsonPoint(0.31, 0.32),
                new GeoJsonPoint(0.11, 0.12)
            )
        );
    }
}