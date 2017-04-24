package com.altomni.apn.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.altomni.apn")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories("com.altomni.apn.repository")
public class AppConfiguration {
	@Resource
	private Environment env;

	@Bean
	public CustomerRoutingDataSource dataSource() {
		CustomerRoutingDataSource dataSource = new CustomerRoutingDataSource();
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(CustomerType.t1, dataSourceT1());
		targetDataSources.put(CustomerType.t2, dataSourceT2());
		targetDataSources.put(CustomerType.t3, dataSourceT3());
		dataSource.setTargetDataSources(targetDataSources);
		dataSource.setDefaultTargetDataSource(dataSourceT1());

		return dataSource;
	}

	@Bean
	public DataSource dataSourceT1() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("spring.datasource1.driverClassName"));
		dataSource.setUrl(env.getRequiredProperty("spring.datasource1.url"));
		dataSource.setUsername(env.getRequiredProperty("spring.datasource1.username"));
		dataSource.setPassword(env.getRequiredProperty("spring.datasource1.password"));
		return dataSource;
	}

	@Bean
	public DataSource dataSourceT2() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("spring.datasource2.driverClassName"));
		dataSource.setUrl(env.getRequiredProperty("spring.datasource2.url"));
		dataSource.setUsername(env.getRequiredProperty("spring.datasource2.username"));
		dataSource.setPassword(env.getRequiredProperty("spring.datasource2.password"));
		return dataSource;
	}

	@Bean
	public DataSource dataSourceT3() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("spring.datasource3.driverClassName"));
		dataSource.setUrl(env.getRequiredProperty("spring.datasource3.url"));
		dataSource.setUsername(env.getRequiredProperty("spring.datasource3.username"));
		dataSource.setPassword(env.getRequiredProperty("spring.datasource3.password"));
		return dataSource;
	}

	@Bean
	public EntityManager entityManager() {
		return entityManagerFactory().getObject().createEntityManager();
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setGenerateDdl(false);
		em.setDataSource(dataSource());
		em.setJpaVendorAdapter(jpaVendorAdapter);
		em.setPackagesToScan("com.altomni.apn.model");
		return em;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

}