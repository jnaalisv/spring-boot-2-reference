package com.acme.http;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductResourceTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void getProductsShouldReturnOK() throws Exception {
        mvc.perform(get("/products"))
            .andExpect(status().isOk());
    }

    @Test
    void postingANewProductShouldReturnOkAndLocationAndNewProduct() throws Exception {
        mvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{\"name\":\"HAL9000\"}")
        )
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                .andExpect(header().string("Location", "http://localhost/products/1"))
                .andExpect(content().json("{\"name\":\"HAL9000\", \"id\":1}"));
    }

    @Nested
    class APostedProduct {

        @Test
        void canBeFetched() throws Exception {
            mvc.perform(
                    get("/products")
            )
                    .andExpect(status().isOk())
                    .andExpect(content().json("[{\"name\":\"HAL9000\", \"id\":1}]"));
        }
    }
}
