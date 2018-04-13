package com.acme.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.acme.domain.Product;
import com.acme.domain.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
        final Product product = new Product(newProductDTO.name);
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
                .map(product -> new ProductDTO(product.getId(), product.getName()))
                .collect(Collectors.toList());
    }

    static class ProductDTO {

        @JsonProperty
        final long id;

        @JsonProperty
        final String name;

        ProductDTO(long id, String name) {
            this.name = name;
            this.id = id;
        }
    }

}
