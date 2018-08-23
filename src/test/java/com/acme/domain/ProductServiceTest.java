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

        ProductDTO product = new ProductDTO(0,0,"Computer Mk.I");
        ProductDTO savedProduct = productService.save(product);

        savedProduct.name = "Computer Mk.II";
        productService.update(savedProduct);
        ProductDTO fromDb = productService.getOne(savedProduct.id);
        assertEquals(fromDb.name, savedProduct.name);
        assertEquals(fromDb.version, savedProduct.version+1);

        fromDb.version = fromDb.version -1;
        fromDb.name = "Mk.III";

        assertThrows(
                OptimisticLockException.class,
                () -> productService.update(fromDb)
        );
    }
}
