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

        ProductDTO newProduct = ProductDTO
                .builder()
                .name("Computer Mk.I")
                .build();

        ProductDTO savedProduct = productService.save(newProduct);

        ProductDTO updateProductWith = ProductDTO
                .builder()
                .id(savedProduct.id())
                .version(savedProduct.version())
                .name("Computer Mk.II")
                .build();

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
