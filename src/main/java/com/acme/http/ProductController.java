package com.acme.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.acme.application.domain.Product;
import com.acme.application.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<?> save(@RequestBody NewProductDTO newProductDTO) {
        final Product product = Product.of(newProductDTO.name);
        productService.save(product);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(product.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    static class NewProductDTO {
        @JsonProperty
        String name;
    }

    @GetMapping("/products")
    public List<ProductDTO> getAll() {
        final List<Product> products = productService.getAll();
        return products
                .stream()
                .map(ProductDTO::of)
                .collect(Collectors.toList());
    }

    @GetMapping("/products/{id}")
    public ProductDTO get(@PathVariable String id) {
        final Product product =  productService.get(id);
        return ProductDTO.of(product);
    }

    static class ProductDTO {

        @JsonProperty
        final String id;

        @JsonProperty
        final String name;

        ProductDTO(String id, String name) {
            this.name = name;
            this.id = id;
        }

        static ProductDTO of(Product product) {
            return new ProductDTO(product.getId(), product.getName());
        }
    }
}
