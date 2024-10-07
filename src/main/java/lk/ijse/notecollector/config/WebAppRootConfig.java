package lk.ijse.notecollector.config;

import jakarta.persistence.EntityManagerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;

@EnableJpaRepositories(basePackages = "lk.ijse.notecollector.dao") /*JPA project ekaka full power eka gnna*/
@EnableTransactionManagement
@Configuration
@ComponentScan(basePackages = "lk.ijse.notecollector")
public class WebAppRootConfig {
    /* database ekak (data thiyana thanak) sambanda wada karanna yaddi thama dataSources ona wenne. */
    /* data ganna puluwan widi - RDBMS, FS-File System, web services, in memory based(meke karala thiyenne me widiyata.) */
    /* type of data sources - Basic data source - mekedi puluwan connection pool ekakin data ganna
    *                         Driver Manager data source - testing walata pawichchi karanne
    *                         ComboPooledDataSource - connection pool ekakin data ganna
    *                         HikariDataSource - production level application walata pawichchi karanne *** meka thama wadagathma ***
    *                         JNDI datasource - Java Naming Directory Interface through config and lookup karanawa*/

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public DataSource dataSource() {

        var dmds = new DriverManagerDataSource();
        dmds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dmds.setUrl("jdbc:mysql://localhost:3306/noteTaker?createDatabaseIfNotExist=true");
        dmds.setUsername("root");
        dmds.setPassword("Ijse@1234");
        return dmds;
    }

    /* ORM walata adalawa configurations thiyenne. */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        /* meka bootstrap karala thiyenne JPA walin */
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(); /* meke session na entity managersla thama inne. */
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter); /* eclipseda hibernateda kiyala vendorwa connect karanne methanin. */
        factory.setPackagesToScan("lk.ijse.notecollector.entity.impl");
        factory.setDataSource(dataSource());
        return factory;
    }

    /* transaction patta handle karanna thama me method eka thiyenne. */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}
