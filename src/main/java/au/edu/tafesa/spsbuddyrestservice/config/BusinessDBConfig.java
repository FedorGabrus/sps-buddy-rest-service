/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.edu.tafesa.spsbuddyrestservice.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configures business data base.
 * Used as primary data base.
 * 
 * @author Fedor Gabrus
 */
@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
        basePackages = {"au.edu.tafesa.spsbuddyrestservice.repository.business"},
        entityManagerFactoryRef = "businessEntityManagerFactory",
        transactionManagerRef = "businessTransactionManager")
public class BusinessDBConfig {
    
    /**
     * Binds data source props from application properties.
     *
     * @return DataSourceProperties
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties businessDataSourceProperties() {
        return new DataSourceProperties();
    }
    
    /**
     * Creates data source.
     * 
     * @param dataSourceProperties businessDataSourceProperties
     * @return DataSource
     */
    @Bean
    @Primary
    public DataSource businessDataSource(
            @Qualifier("businessDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    
    /**
     * Configures entity manager factory.
     * 
     * @param builder EntityManagerFactoryBuilder
     * @param dataSource businessDataSource
     * @return LocalContainerEntityManagerFactoryBean
     */
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean businessEntityManagerFactory(EntityManagerFactoryBuilder builder,
            @Qualifier("businessDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("au.edu.tafesa.spsbuddyrestservice.entity.business")
                .persistenceUnit("businessPU")
                .build();
    }
    
    /**
     * Configures transaction manager.
     * 
     * @param entityManagerFactory businessEntityManagerFactory
     * @return PlatformTransactionManager
     */
    @Bean
    @Primary
    public PlatformTransactionManager businessTransactionManager(
            @Qualifier("businessEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        var txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        
        return txManager;
    }
    
}
