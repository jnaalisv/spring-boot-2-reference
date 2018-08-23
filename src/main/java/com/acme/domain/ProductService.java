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
        return productRepository
                .getOne(productId)
                .orElseThrow(() -> productNotFoundById(productId));
    }

    @Transactional
    public Product update(Long productId, long version, String name) {
        final Product product = new Product(productId, version, name);
        productRepository.update(product);
        return product;
    }

    @Transactional
    public void delete(Long productId) {
        Product product = productRepository
                .getOne(productId)
                .orElseThrow(() -> productNotFoundById(productId));

        productRepository.delete(product);
    }

    private static NotFoundException productNotFoundById(Long productId) {
        return NotFoundException.entityNotFound(Product.class, "id="+productId);
    }
}
