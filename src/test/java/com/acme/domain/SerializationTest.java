package com.acme.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SerializationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSerialization() throws IOException {
        ProductDTO productDTO = new ProductDTO(1, 6, "Computer I");

        String json = objectMapper.writeValueAsString(productDTO);

        assertEquals("""
                {"id":1,"version":6,"name":"Computer I"}\
                """, json);
    }

    @Test
    void testDeserialization() throws IOException {

        var json = """
                {
                    "id":1,
                    "version":6,
                    "name":"Computer I"
                }
                """;

        ProductDTO deSerializedJson = objectMapper.readValue(json, ProductDTO.class);

        assertEquals(1, deSerializedJson.id());
        assertEquals(6, deSerializedJson.version());
        assertEquals("Computer I", deSerializedJson.name());
    }
}
