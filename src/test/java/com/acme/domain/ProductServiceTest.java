package com.acme.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.OptimisticLockException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void testOptimisticLocking() {

        ProductDTO newProduct = new ProductDTO(0, 0, "Computer Mk.I");

        ProductDTO savedProduct = productService.save(newProduct);

        ProductDTO updateProductWith = new ProductDTO(savedProduct.id(), savedProduct.version(), "Computer Mk.II");

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
