package com.acme.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductDTO(
        @JsonProperty("id") long id
        , @JsonProperty("version") long version
        , @JsonProperty("name") String name) {

    static ProductDTO from(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getVersion(),
                product.getName());
    }

    public Product toEntity() {
        return new Product(id(), version(), name());
    }
}
