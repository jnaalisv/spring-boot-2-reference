package com.acme.http.alt;

import com.acme.domain.ProductDTO;
import com.acme.domain.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AltProductController {

    private final ProductService productService;

    public AltProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/alt-products")
    public ResponseEntity<ProductView> save(@RequestBody NewProduct newProduct) {
        final var savedProduct = productService.save(new ProductDTO(0, 0, newProduct.name));

        final var createdProduct = new ProductView(
                savedProduct.id,
                savedProduct.name,
                savedProduct.version
        );

        final var location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdProduct.id).toUri();

        return ResponseEntity
                .created(location)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(createdProduct);
    }

    @GetMapping("/alt-products")
    public List<ProductView> getAll() {
        return productService
                .getAll()
                .stream()
                .map(productDTO -> new ProductView(
                        productDTO.id,
                        productDTO.name,
                        productDTO.version
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/alt-products/{productId}")
    public ProductView getOne(@PathVariable Long productId) {

        var productDTO = productService.getOne(productId);
        return new ProductView(
                productDTO.id,
                productDTO.name,
                productDTO.version
        );
    }

    @DeleteMapping("/alt-products/{productId}")
    public void delete(@PathVariable Long productId) {
        productService.delete(productId);
    }

}
