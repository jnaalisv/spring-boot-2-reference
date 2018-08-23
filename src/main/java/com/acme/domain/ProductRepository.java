package com.acme.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    void save(Product product);

    List<Product> getAll();

    Optional<Product> getOne(long productId);

    void delete(Product product);
}
