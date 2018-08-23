package com.acme.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void testOptimisticLocking() {

        Product product = new Product("Computer Mk.I");
        productService.save(product);

        productService.update(product.getId(), product.getVersion(), "Computer Mk.II");
        Product fromDb = productService.getOne(product.getId());
        assertEquals(fromDb.getName(), "Computer Mk.II");
        assertEquals(fromDb.getVersion(), product.getVersion()+1);

        assertThrows(
                HibernateOptimisticLockingFailureException.class,
                () -> productService.update(fromDb.getId(), fromDb.getVersion()-1, "Mk.III")
        );
    }
}
