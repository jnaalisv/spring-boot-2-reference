package com.acme.application.domain;

import java.util.UUID;

public class Product {

    private String id;
    private String name;

    public static Product of(String name) {
        return new Product(name);
    }

    private Product(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public Product(String domainId, String name) {
        this.id = domainId;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
