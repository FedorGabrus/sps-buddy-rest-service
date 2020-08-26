/*
 * Copyright 2020 TAFE SA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package au.edu.tafesa.spsbuddyrestservice.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
    public DataSource businessDataSource(DataSourceProperties dataSourceProperties) {
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
            DataSource dataSource) {
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
    public PlatformTransactionManager businessTransactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
    
}
