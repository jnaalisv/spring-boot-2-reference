package com.acme.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@JsonSerialize(as = ImmutableProductDTO.class)
@JsonDeserialize(as = ImmutableProductDTO.class)
@Value.Immutable
public interface ProductDTO {

    @Value.Default
    default long id() {
        return 0;
    };

    @Value.Default
    default long version() {
        return 0;
    }

    String name();

    static ProductDTO from(Product product) {
        return builder()
                .name(product.getName())
                .id(product.getId())
                .version(product.getVersion())
                .build();
    }

    default Product toEntity() {
        return new Product(id(), version(), name());
    }

    static ImmutableProductDTO.Builder builder() {
        return ImmutableProductDTO.builder();
    }
}
