package uk.co.noxtech.util.jackson;

import org.junit.Before;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractGeoJsonDeserializerTest {

    DeserializationContext context;
    ObjectMapper objectMapper;
    JsonParser parser;

    @Before
    public void abstractSetUp() throws Exception {
        objectMapper = new ObjectMapper();
        context = objectMapper.getDeserializationContext();
    }
}
