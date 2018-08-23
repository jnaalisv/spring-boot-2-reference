package com.acme.http;

import com.acme.domain.Product;
import com.acme.domain.ProductService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    static class NewProductDTO {
        @JsonProperty
        String name;
    }

    @PostMapping("/products")
    public ResponseEntity<?> save(@RequestBody NewProductDTO newProductDTO) {
        final Product product = new Product(newProductDTO.name);
        productService.save(product);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(product.getId()).toUri();

        return ResponseEntity
                .created(location)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(new ProductDTO(product.getId(), product.getName()));
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

    @GetMapping("/products")
    public List<ProductDTO> getAll() {
        final List<Product> products = productService.getAll();
        return products
                .stream()
                .map(product -> new ProductDTO(product.getId(), product.getName()))
                .collect(Collectors.toList());
    }

    @GetMapping("/products/{productId}")
    public ProductDTO getOne(@PathVariable Long productId) {
        final Product product = productService.getOne(productId);
        return new ProductDTO(product.getId(), product.getName());
    }

    static class UpdateProductDTO {

        final String name;

        @JsonCreator
        UpdateProductDTO(@JsonProperty("name") String name) {
            this.name = name;
        }
    }

    @PutMapping("/products/{productId}")
    public ProductDTO update(@PathVariable Long productId, @RequestBody UpdateProductDTO updateProductDTO) {
        final Product updatedProduct = productService.update(productId, updateProductDTO.name);
        return new ProductDTO(updatedProduct.getId(), updatedProduct.getName());
    }

}
