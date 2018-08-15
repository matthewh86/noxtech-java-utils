package uk.co.noxtech.util.jackson;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.noxtech.util.Resource.getResource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.geo.GeoJsonLineString;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

public class GeoJsonLineStringDeserializerTest extends AbstractGeoJsonDeserializerTest {

    private GeoJsonLineStringDeserializer underTest;

    @Before
    public void setUp() throws Exception {
        parser = objectMapper.getFactory().createParser(getResource("jsonLine.json"));

        underTest = new GeoJsonLineStringDeserializer();
    }

    @Test
    public void deserialize_shouldDeserializeGeoJsonPoint_fromJson() throws Exception {
        GeoJsonLineString result = underTest.deserialize(parser, context);

        assertThat(result).isEqualTo(
            new GeoJsonLineString(
                new GeoJsonPoint(0.11, 0.12),
                new GeoJsonPoint(0.21, 0.22)
            ));
    }

}
