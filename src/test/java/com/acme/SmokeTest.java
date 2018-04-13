package com.acme;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = ExampleApplication.class
)
public class SmokeTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void applicationShouldRespond() {
        assertThat(
                restTemplate
                        .getForEntity("/products", String.class)
                        .getStatusCode()
        ).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldBeAbleToAddAndListProducts() {
        assertThat(
                restTemplate
                .getForEntity("/products", List.class)
                .getBody()
                .size()
        ).isEqualTo(0);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        final HttpEntity entity = new HttpEntity<>("{\"name\":\"NewThingy\"}", httpHeaders);

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
