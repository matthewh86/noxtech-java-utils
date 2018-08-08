package uk.co.noxtech.util.jackson;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.noxtech.util.Resource.getResource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public class GeoJsonPointDeserializerTest extends AbstractGeoJsonDeserializerTest {

    private GeoJsonPointDeserializer underTest;

    @Before
    public void setUp() throws Exception {
        parser = objectMapper.getFactory().createParser(getResource("jsonPoint.json"));

        underTest = new GeoJsonPointDeserializer();
    }

    @Test
    public void deserialize() throws Exception {
        GeoJsonPoint result = underTest.deserialize(parser, context);

        assertThat(result).isEqualTo(new GeoJsonPoint(0.1, 0.2));
    }

}
