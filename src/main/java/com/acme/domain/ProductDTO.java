package com.acme.domain;

public class ProductDTO {

    public long id;
    public long version;
    public String name;

    public ProductDTO(long id, long version, String name) {
        this.id = id;
        this.version = version;
        this.name = name;
    }

    static ProductDTO from(Product product) {
        return new ProductDTO(product.getId(), product.getVersion(), product.getName());
    }

    Product toEntity() {
        return new Product(id, version, name);
    }
}
