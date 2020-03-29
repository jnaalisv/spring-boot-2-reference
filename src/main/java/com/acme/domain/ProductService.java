package com.acme.domain;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductDTO save(ProductDTO productDTO) {
        final Product product = productDTO.toEntity();
        productRepository.save(product);
        return ProductDTO.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAll() {
        return productRepository
                .getAll()
                .stream()
                .map(ProductDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO getOne(long productId) {
        return productRepository
                .getOne(productId)
                .map(ProductDTO::from)
                .orElseThrow(() -> productNotFoundById(productId));
    }

    @Transactional
    public ProductDTO update(ProductDTO productDTO) {
        final Product product = productDTO.toEntity();
        productRepository.update(product);
        productRepository.flush();
        return ProductDTO.from(product);
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
