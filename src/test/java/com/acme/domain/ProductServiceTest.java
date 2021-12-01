package com.acme.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.OptimisticLockException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void testOptimisticLocking() {

        var newProduct = new ProductDTO(0, 0, "Computer Mk.I");

        var savedProduct = productService.save(newProduct);

        var updateProductWith = new ProductDTO(
                savedProduct.id(),
                savedProduct.version(),
                "Computer Mk.II");

        productService.update(updateProductWith);

        ProductDTO updatedProduct = productService.getOne(savedProduct.id());
        assertEquals(updatedProduct.name(), updateProductWith.name());
        assertEquals(updatedProduct.version(), updateProductWith.version()+1);

        assertThrows(
                OptimisticLockException.class,
                () -> productService.update(updateProductWith)
        );
    }
}
