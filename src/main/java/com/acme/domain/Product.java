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

    Product() { /* for hibernate */}

    private String name;

    Product(long id, long version, String name) {
        this.id = id;
        this.version = version;
        this.name = name;
    }

    long getId() {
        return id;
    }

    String getName() {
        return name;
    }

    long getVersion() {
        return version;
    }
}
