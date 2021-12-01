package com.acme.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private long id;

    @Version
    private long version;

    public Product() { /* for hibernate */}

    private String name;

    Product(long id, long version, String name) {
        this.id = id;
        this.version = version;
        this.name = name;
    }

    ProductDTO toDto() {
        return new ProductDTO(id,version,name);
    }

}
