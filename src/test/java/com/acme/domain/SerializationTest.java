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
        var productDTO = new ProductDTO(1, 6, "Computer I");

        var json = objectMapper.writeValueAsString(productDTO);

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

        var productDto = objectMapper.readValue(json, ProductDTO.class);

        assertEquals(1, productDto.id());
        assertEquals(6, productDto.version());
        assertEquals("Computer I", productDto.name());
    }
}
