package com.acme.http;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductResourceTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void initiallyNoProducts() throws Exception {
        mvc.perform(get("/products"))
            .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Nested
    class aNewProduct {

        @Test
        void canBeCreated() throws Exception {
            mvc.perform(
                    post("/products")
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                            .content("{\"name\":\"HAL9000\"}")
            )
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Content-Type", "application/json;charset=UTF-8"))
                    .andExpect(header().string("Location", "http://localhost/products/1"))
                    .andExpect(content().json("{\"name\":\"HAL9000\", \"id\":1, \"version\":0}"));
        }

        @Nested
        class andThen {

            @Test
            void fetchedWithAllOthers() throws Exception {
                mvc.perform(
                        get("/products")
                )
                        .andExpect(status().isOk())
                        .andExpect(content().json("[{\"name\":\"HAL9000\", \"id\":1, \"version\":0}]"));
            }

            @Test
            void fetchedById() throws Exception {
                mvc.perform(
                        get("/products/1")
                )
                        .andExpect(status().isOk())
                        .andExpect(content().json("{\"name\":\"HAL9000\", \"id\":1, \"version\":0}"));
            }

            @Test
            void getByInvalidIdReturnsNotFound() throws Exception {
                mvc.perform(
                        get("/products/456436743")
                )
                        .andExpect(status().isNotFound());
            }

            @Nested
            class update {

                @Test
                void returnsTheUpdatedProductAndHttpOK() throws Exception {
                    mvc.perform(
                            put("/products/1")
                                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                                    .content("{\"name\":\"CRAY-1\", \"version\":0}")
                    )
                            .andExpect(status().isOk())
                            .andExpect(content().json("{\"name\":\"CRAY-1\", \"id\":1, \"version\":1}"));
                }

                @Test
                void byInvalidIdReturnsConflict() throws Exception {
                    mvc.perform(
                            put("/products/994235234")
                                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                                    .content("{\"name\":\"CRAY-994235234\", \"version\":0}")
                    )
                            .andExpect(status().isConflict());
                }

                @Test
                void byInvalidVersionReturnsConflict() throws Exception {
                    mvc.perform(
                            put("/products/1")
                                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                                    .content("{\"name\":\"CRAY-1\", \"version\":0}")
                    );

                    mvc.perform(
                            put("/products/1")
                                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                                    .content("{\"name\":\"CRAY-2\", \"version\":0}")
                    )
                            .andExpect(status().isConflict());
                }

                @Nested
                class andThenDelete {

                    @Test
                    void byIdIsSuccessful() throws Exception {
                        mvc.perform(delete("/products/1"))
                                .andExpect(status().isOk());
                    }

                    @Test
                    void byInvalidIdReturnsNotFound() throws Exception {
                        mvc.perform(delete("/products/1"))
                                .andExpect(status().isNotFound());
                    }

                }
            }


        }
    }


}
