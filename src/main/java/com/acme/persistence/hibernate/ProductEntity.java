package com.acme.persistence.hibernate;

import com.acme.application.domain.Product;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@DynamicInsert
@DynamicUpdate
public class ProductEntity {

    @Id
    @GeneratedValue
    private long id;
    private String domainId;
    private String name;

    ProductEntity() { /* for hibernate */}

    private ProductEntity(String domainId, String name) {
        this.domainId = domainId;
        this.name = name;
    }

    public static ProductEntity from(Product product) {
        return new ProductEntity(product.getId(), product.getName());
    }

    public Product toDomainModel() {
        return new Product(domainId, name);
    }
}
