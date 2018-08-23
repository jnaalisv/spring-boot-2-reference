package com.acme.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    @Transactional(readOnly = true)
    public Product getOne(long productId) {
        return productRepository.getOne(productId);
    }

    @Transactional
    public Product update(Long productId, String name) {
        Product product = productRepository.getOne(productId);
        product.update(name);
        return product;
    }
}
