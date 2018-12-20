package com.refresh.spring.config;

import com.refresh.spring.model.CustomerDBProps;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.refresh.spring.repo")
public class AppConfig {

    @Bean
    @ConfigurationProperties(prefix = "sample-app.customer-db")
    public CustomerDBProps customerDBProps(){
        return new CustomerDBProps();
    }

    @Bean
    DataSource hikariDataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(customerDBProps().getUrl().trim());
        dataSource.setDriverClassName(customerDBProps().getDriverClass().trim());
        dataSource.setUsername(customerDBProps().getUsername());
        dataSource.setPassword(customerDBProps().getPassword());
        dataSource.setMaximumPoolSize(50);
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setPrepareConnection(true);
        adapter.setGenerateDdl(true);
        adapter.setDatabasePlatform("org.hibernate.dialect.MariaDBDialect");
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setJpaVendorAdapter(adapter);
        factoryBean.setPackagesToScan("com.refresh.spring.repo.dto");
        factoryBean.setDataSource(hikariDataSource());
        return factoryBean;
    }
    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }


}
