package com.acme.persistence;

import com.acme.domain.Product;
import com.acme.domain.ProductRepository;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class HibernateProductRepository implements ProductRepository {

    private final SessionFactory sessionFactory;

    public HibernateProductRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Product product) {
        sessionFactory
                .getCurrentSession()
                .persist(product);
    }

    @Override
    public List<Product> getAll() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Product", Product.class)
                .list();
    }

    @Override
    public Optional<Product> getOne(long productId) {
        return Optional.ofNullable(sessionFactory
                .getCurrentSession()
                .find(Product.class, productId));
    }

    @Override
    public void delete(Product product) {
        sessionFactory.getCurrentSession().delete(product);
    }

    @Override
    public void update(final Product product) {
        sessionFactory.getCurrentSession().update(product);
    }

    @Override
    public void flush() {
        sessionFactory.getCurrentSession().flush();
    }
}
