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
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configures spring security and app users data base.
 *
 * @author Fedor Gabrus
 */
//@Configuration
//@EnableTransactionManagement
//@PropertySource({"classpath:application.properties"})
//@EnableJpaRepositories(
//        basePackages = {"au.edu.tafesa.spsbuddyrestservice.repository.user"},
//        entityManagerFactoryRef = "usersEntityManagerFactory",
//        transactionManagerRef = "usersTransactionManager"
//)
public class UsersDBConfig {
//
//    /**
//     * Binds data source props from application properties.
//     *
//     * @return DataSourceProperties
//     */
//    @Bean
//    @ConfigurationProperties(prefix = "spring.users-datasource")
//    public DataSourceProperties usersDataSourceProps() {
//        return new DataSourceProperties();
//    }
//
//    /**
//     * Creates data source.
//     * 
//     * @param dataSourceProperties usersDataSourceProps
//     * @return DataSource
//     */
//    @Bean
//    public DataSource usersDataSource(@Qualifier("usersDataSourceProps") DataSourceProperties dataSourceProperties) {
//        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }
//    
//    /**
//     * Configures entity manager factory.
//     * 
//     * @param builder EntityManagerFactoryBuilder
//     * @param dataSource usersDataSource
//     * @return LocalContainerEntityManagerFactoryBean
//     */
//    @Bean
//    public LocalContainerEntityManagerFactoryBean usersEntityManagerFactory(EntityManagerFactoryBuilder builder,
//            @Qualifier("usersDataSource") DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages("au.edu.tafesa.spsbuddyrestservice.entity.user")
//                .persistenceUnit("usersPU")
//                .build();
//    }
//
//    /**
//     * Configures transaction manager.
//     * 
//     * @param entityManagerFactory usersEntityManagerFactory
//     * @return PlatformTransactionManager
//     */
//    @Bean
//    public PlatformTransactionManager usersTransactionManager(
//            @Qualifier("usersEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
    
}
