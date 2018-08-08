package uk.co.noxtech.util.jackson;

import java.io.StringWriter;

import org.junit.Before;
import org.springframework.data.geo.GeoModule;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

public abstract class AbstractGeoJsonSerializerTest {

    SerializerProvider serializerProvider;
    JsonGenerator generator;
    StringWriter writer;

    @Before
    public void abstractSetUp() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new GeoModule());
        serializerProvider = objectMapper.getSerializerProvider();
        writer = new StringWriter();
        generator = new JsonFactory().createGenerator(writer);
        generator.setCodec(objectMapper);
    }

}
