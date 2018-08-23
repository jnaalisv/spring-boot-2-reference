package com.acme;

import com.zaxxer.hikari.HikariDataSource;
import net.ttddyy.dsproxy.listener.logging.SLF4JLogLevel;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties
public class HibernateConfiguration {

    @Bean
    @ConfigurationProperties("spring.jpa.properties")
    public Properties hibernateProperties() {
        return new Properties();
    }

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource, Properties hibernateProperties) {
        return new LocalSessionFactoryBuilder(dataSource)
                .scanPackages("com.acme.domain")
                .addProperties(hibernateProperties)
                .buildSessionFactory();
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    public DataSource dataSource(DataSourceProperties properties) {
        final HikariDataSource dataSource = properties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();

        if (properties.getName() != null) {
            dataSource.setPoolName(properties.getName());
        }
        return ProxyDataSourceBuilder.create(dataSource)
                .name("HikariDS")
                .multiline()
                .logQueryBySlf4j(SLF4JLogLevel.INFO)
                .build();
    }
}