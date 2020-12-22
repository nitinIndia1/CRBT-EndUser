/*package com.crbt.api.services.config;

import javax.persistence.EntityManagerFactory;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan( basePackages = {"com.crbt.api.services.domain"} )
@EnableJpaRepositories( basePackages = {"com.crbt.api.services.repository"} )
@EnableTransactionManagement
public class DatabaseConfig {

	protected static final String PROPERTY_NAME_DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    protected static final String PROPERTY_NAME_DATABASE_PASSWORD = "wpit@1234";
	// protected static final String PROPERTY_NAME_DATABASE_PASSWORD = "123456";
	 protected static final String PROPERTY_NAME_DATABASE_URL = "jdbc:mysql://localhost:3306/rbtlibyana?autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8";
     protected static final String PROPERTY_NAME_DATABASE_USERNAME = "root";
     private static final String PROPERTY_PACKAGES_TO_SCAN = "com.crbt.api.services.domain";
    
    @Bean
    public EntityManagerFactory entityManagerFactory() {

      HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      vendorAdapter.setGenerateDdl(true);

      LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
      factory.setJpaVendorAdapter(vendorAdapter);
      factory.setPackagesToScan(PROPERTY_PACKAGES_TO_SCAN);
      factory.setDataSource(dataSource());
      factory.afterPropertiesSet();
      return factory.getObject();
    }

    @Bean
    public BasicDataSource dataSource(){
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(PROPERTY_NAME_DATABASE_DRIVER);
        ds.setUrl(PROPERTY_NAME_DATABASE_URL);
        ds.setUsername(PROPERTY_NAME_DATABASE_USERNAME);
        ds.setPassword(PROPERTY_NAME_DATABASE_PASSWORD);
        ds.setInitialSize(5);
        return ds;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() {

      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(entityManagerFactory());
      return transactionManager;
    }


}
*/