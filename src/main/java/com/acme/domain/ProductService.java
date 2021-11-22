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
    public ProductDTO save(ProductDTO dto) {
        var product = new Product(dto.id(), dto.version(), dto.name());
        productRepository.save(product);
        return product.toDto();
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAll() {
        return productRepository
                .getAll()
                .stream()
                .map(Product::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDTO getOne(long productId) {
        return productRepository
                .getOne(productId)
                .map(Product::toDto)
                .orElseThrow(() -> productNotFoundById(productId));
    }

    @Transactional
    public ProductDTO update(ProductDTO productDTO) {
        var product = new Product(productDTO.id(), productDTO.version(), productDTO.name());
        productRepository.update(product);
        productRepository.flush();
        return product.toDto();
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
