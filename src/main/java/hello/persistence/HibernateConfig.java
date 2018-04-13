package hello.persistence;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class HibernateConfig {

    private static Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.hbm2ddl.auto","create");
        hibernateProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
        hibernateProperties.put("hibernate.format_sql", "true");
        hibernateProperties.put("hibernate.generate_statistics", "true");
        hibernateProperties.put("hibernate.jdbc.batch_size", 30);
        hibernateProperties.put("hibernate.order_updates", "true");
        hibernateProperties.put("hibernate.order_inserts", "true");
        hibernateProperties.put("hibernate.jdbc.batch_versioned_data", "true");
        return hibernateProperties;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("hello.domain");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }
}
