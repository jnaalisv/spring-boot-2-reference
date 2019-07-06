package com.acme.http;

import com.acme.domain.ProductDTO;
import com.acme.domain.ProductService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping("/products")
    public ResponseEntity<?> save(@RequestBody ProductDTO newProductDTO) {
        final ProductDTO savedProduct = productService.save(newProductDTO);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedProduct.id()).toUri();

        return ResponseEntity
                .created(location)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(savedProduct);
    }

    @GetMapping("/products")
    public List<ProductDTO> getAll() {
        return productService.getAll();
    }

    @GetMapping("/products/{productId}")
    public ProductDTO getOne(@PathVariable Long productId) {
        return productService.getOne(productId);
    }

    @PutMapping("/products/{productId}")
    public ProductDTO update(@PathVariable Long productId, @RequestBody ProductDTO updateProductDTO) {
        return productService.update(updateProductDTO);
    }

    @DeleteMapping("/products/{productId}")
    public void delete(@PathVariable Long productId) {
        productService.delete(productId);
    }

}
