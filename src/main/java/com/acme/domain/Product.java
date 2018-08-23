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

    public Product(String name) {
        this.name = name;
    }

    public Product(long id, long version, String name) {
        this.id = id;
        this.version = version;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getVersion() {
        return version;
    }
}
