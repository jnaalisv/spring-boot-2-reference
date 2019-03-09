package com.acme.http.alt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class NewProduct {

    final String name;

    /**
     * Instruct Jackson how to deserialize JSON
     * @param name
     */
    @JsonCreator
    NewProduct(@JsonProperty("name") final String name) {
        this.name = name;
    }

}
