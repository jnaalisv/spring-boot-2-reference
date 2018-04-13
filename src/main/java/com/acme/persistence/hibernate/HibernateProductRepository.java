package com.acme.persistence.hibernate;

import com.acme.application.domain.Product;
import com.acme.application.domain.ProductRepository;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.stream.Collectors;

public class HibernateProductRepository implements ProductRepository {

    private final SessionFactory sessionFactory;

    public HibernateProductRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Product product) {
        sessionFactory
                .getCurrentSession()
                .persist(ProductEntity.from(product));
    }

    @Override
    public List<Product> getAll() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from ProductEntity", ProductEntity.class)
                .list()
                .stream()
                .map(ProductEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Product get(String id) {
        final ProductEntity productEntity = sessionFactory
                .getCurrentSession()
                .createQuery("from ProductEntity where domainId = :domainId", ProductEntity.class)
                .setParameter("domainId", id)
                .uniqueResultOptional()
                .orElseThrow(() -> new RuntimeException("not Found by id " + id));

        return productEntity.toDomainModel();
    }
}
