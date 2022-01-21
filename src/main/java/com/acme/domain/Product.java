package com.acme.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

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
