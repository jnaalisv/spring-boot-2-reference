package com.acme;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ExampleApplication.class
)
class SmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void applicationShouldRespond() {
        assertThat(
                restTemplate
                        .getForEntity("/products", String.class)
                        .getStatusCode()
        ).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldBeAbleToAddAndListProducts() {
        assertThat(
                restTemplate
                .getForEntity("/products", List.class)
                .getBody()
                .size()
        ).isEqualTo(0);

        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        var requestBody = """
                {"name":"NewThingy"}
                """;
        var entity = new HttpEntity<>(requestBody, httpHeaders);

        assertThat(
                restTemplate
                        .postForLocation("/products", entity, String.class)
                        .getPath()
        ).isEqualTo("/products/1");

        assertThat(
                restTemplate
                        .getForEntity("/products", List.class)
                        .getBody()
                        .size()
        ).isEqualTo(1);
    }

}
