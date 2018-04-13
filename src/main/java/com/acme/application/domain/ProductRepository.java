package com.acme.application.domain;

import java.util.List;

public interface ProductRepository {

    void save(Product product);

    List<Product> getAll();

    Product get(String id);
}
