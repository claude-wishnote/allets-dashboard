package com.allets.backend.dashboard.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jdbc.query.QueryDslJdbcTemplate;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = { "com.allets.backend.dashboard.server.repository.common" }, entityManagerFactoryRef = "commonEntityManagerFactory", transactionManagerRef = "commonTxManager")
@EnableTransactionManagement
public class JPAConfig {

	@Value("${backend.dashboard.common.jpa.entity.package}")
	String entityPackage;

	@Value("${backend.dashboard.hibernate.dialect.mysql}")
	String dialect;

	@Value("${backend.dashboard.common.jpa.unit}")
	String commonJpaUnit;

	@Value("${backend.dashboard.common.jpa.hbm2ddl.auto}")
	String commonHbm2ddlAuto;

	@Value("${backend.dashboard.common.show.sql}")
	String commonShowSql;

	@Bean
	public LocalContainerEntityManagerFactoryBean commonEntityManagerFactory(
			DataSource dataSource) {

		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setPersistenceUnitName(commonJpaUnit);
		emf.setDataSource(dataSource);
		emf.setPackagesToScan(new String[] { entityPackage });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		emf.setJpaVendorAdapter(vendorAdapter);
		emf.setJpaProperties(additionalCommonProperties());

		return emf;
	}

	Properties additionalCommonProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto",
				commonHbm2ddlAuto);
		properties.setProperty("hibernate.dialect", dialect);
		properties.setProperty("hibernate.show_sql", commonShowSql);

		return properties;
	}

	@Bean
	public PlatformTransactionManager commonTxManager(
			EntityManagerFactory commonEntityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager
				.setEntityManagerFactory(commonEntityManagerFactory);
		return transactionManager;
	}

	@Bean
    public QueryDslJdbcTemplate commonQueryDslTemplate(DataSource dataSource) {
        QueryDslJdbcTemplate template = new QueryDslJdbcTemplate(
				dataSource);
        return template;
    }
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}
