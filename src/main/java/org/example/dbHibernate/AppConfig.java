package org.example.dbHibernate;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("jdbc.properties")
@PropertySource("hibernate.properties")
@ComponentScan("org.example.DAO")
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource webDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        ds.setUrl(env.getProperty("jdbc.url"));
        ds.setUsername(env.getProperty("jdbc.username"));
        ds.setPassword(env.getProperty("jdbc.password"));
        return ds;
    }

//    @Bean
//    public LocalContainerEntityManagerFactoryBean emf() {
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//
//        emf.setDataSource(webDataSource());
//        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        emf.setPackagesToScan("org.example.DAO");
//        return emf;
//    }

//    @Bean
//    public LocalSessionFactoryBean localSessionFactoryBean() {
//        LocalSessionFactoryBean ess = new LocalSessionFactoryBean();
//        ess.setDataSource(webDataSource());
//        ess.setJtaTransactionManager(new HibernateJpaVendorAdapter());
//        ess.setPackagesToScan("org.example.DAO");
//        return ess;
//    }

//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(webDataSource());
//        sessionFactory.setPackagesToScan("org.example.DAO");
//        sessionFactory.setHibernateProperties(new Properties());
//
//        return sessionFactory;
//    }

    @Bean
    LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(webDataSource());
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagestoscan"));
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        hibernateProperties.put("hibernate.max_fetch_depth", env.getProperty("hibernate.max_fetch_depth", Integer.class, 6));
        hibernateProperties.put("hibernate.jdbc.fetch_size", env.getProperty("hibernate.jdbc.fetch_size", Integer.class, 100));
        hibernateProperties.put("hibernate.jdbc.batch_size", env.getProperty("hibernate.jdbc.batch_size", Integer.class, 20));
        hibernateProperties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    @Bean
    AbstractPlatformTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }



//    @Bean
//    public JpaTransactionManager transactionManager() {
//        JpaTransactionManager tx = new JpaTransactionManager();
//        tx.setEntityManagerFactory(ess.);
//        return tx;
//    }


//    @Bean
//    public PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
//        return new PersistenceExceptionTranslationPostProcessor();
//    }

//    @Bean
//    public JdbcTemplate jdbcTemplate() {
//        return new JdbcTemplate(webDataSource());
//    }


}
