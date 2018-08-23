package com.acme;

import com.acme.domain.ProductDTO;
import com.acme.http.ProductMixin;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeJackson() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.mixIn(ProductDTO.class, ProductMixin.class);
        };
    }
}
