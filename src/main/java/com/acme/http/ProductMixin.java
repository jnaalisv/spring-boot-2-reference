package com.acme.http;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ProductMixin {
    ProductMixin(
            @JsonProperty("id") long id,
            @JsonProperty("version") long version,
            @JsonProperty("name") String name
            ) { }
}
