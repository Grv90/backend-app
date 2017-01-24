package com.indiareads.service.boot;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

/*
 *  @version     1.0, 30-Dec-2016
 *  @author gaurav
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.indiareads.service.repo", entityManagerFactoryRef="indiareadsEM", transactionManagerRef="indiareadsTM")
public class PersistenceContext {

	protected static final String PROPERTY_NAME_DATABASE_DRIVER 			= "indiareads.db.driver";
	protected static final String PROPERTY_NAME_DATABASE_PASSWORD 			= "indiareads.db.password";
	protected static final String PROPERTY_NAME_DATABASE_URL 				= "indiareads.db.url";
	protected static final String PROPERTY_NAME_DATABASE_USERNAME 			= "indiareads.db.username";
	protected static final String PROPERTY_NAME_DATABASE_MAX_CONNECTIONS 	= "indiareads.db.maxconnections";
	protected static final String PROPERTY_NAME_DATABASE_MIN_CONNECTIONS 	= "indiareads.db.minconnections";
	protected static final String PROPERTY_NAME_DATABASE_MAX_PARTITIONS 	= "indiareads.db.maxpartitions";
	protected static final String PROPERTY_NAME_DATABASE_MAX_LIFETIME		="indiareads.db.maxlifetimeinmillis";
	protected static final String PROPERTY_NAME_DATABASE_CONNECTION_TIMEOUT ="indiareads.db.connectiontimeoutinmillis";


	private static final String PROPERTY_NAME_HIBERNATE_DIALECT 		= "hibernate.dialect";
	private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL 		= "hibernate.format_sql";
	private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO 	= "hibernate.hbm2ddl.auto";
	private static final String PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
	private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL 		= "hibernate.show_sql";
	private static final String PROPERTY_NAME_HIBERNATE_LAZY_LOAD 		= "hibernate.enable_lazy_load_no_trans";
	private static final String PROPERTY_NAME_HIBERNATE_ENVERS_AUDIT_TABLE_SUFFIX 		= "org.hibernate.envers.audit_table_suffix";
	private static final String PROPERTY_NAME_HIBERNATE_ENVERS_REVISION_FIELD_NAME 		= "org.hibernate.envers.revision_field_name";
	private static final String PROPERTY_NAME_HIBERNATE_ENVERS_REVISION_TYPE_FIELD_NAME 		= "org.hibernate.envers.revision_type_field_name";
	private static final String PROPERTY_NAME_HIBERNATE_ENVERS_REVISION_ON_COLLECTION_CHANGE 		= "org.hibernate.envers.revision_on_collection_change";
	private static final String PROPERTY_NAME_HIBERNATE_ENVERS_AUDIT_STRATEGY 		= "org.hibernate.envers.audit_strategy";
	private static final String PROPERTY_NAME_HIBERNATE_ENVERS_AUDIT_STRATEGY_VALIDITY_END_REV_FIELD_NAME 		= "org.hibernate.envers.audit_strategy_validity_end_rev_field_name";
	private static final String PROPERTY_NAME_HIBERNATE_ENVERS_AUDIT_STRATEGY_VALIDITY_STORE_REVEND_TIMESTAMP 		= "org.hibernate.envers.audit_strategy_validity_store_revend_timestamp";
	private static final String PROPERTY_NAME_HIBERNATE_ENVERS_AUDIT_STRATEGY_VALIDITY_REVEND_TIMESTAMP_FIELD_NAME 		= "org.hibernate.envers.audit_strategy_validity_revend_timestamp_field_name";

	private static final String PROPERTY_PACKAGES_TO_SCAN = "com.indiareads.service.domain";

	@Autowired
	private Environment environment;

	@Bean(name="indiareadsDataStore")
	public DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
		dataSource.setJdbcUrl(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
		dataSource.setUsername(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
		dataSource.setPassword(environment.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
		dataSource.setConnectionTestQuery("SELECT 1");
		dataSource.setMaximumPoolSize(Integer.parseInt(environment.getProperty(PROPERTY_NAME_DATABASE_MAX_CONNECTIONS)));
		dataSource.setMaxLifetime(Long.parseLong(environment.getProperty(PROPERTY_NAME_DATABASE_MAX_LIFETIME)));
		dataSource.setConnectionTimeout(Long.parseLong(environment.getProperty(PROPERTY_NAME_DATABASE_CONNECTION_TIMEOUT)));
		return dataSource;

	}

	@Bean(name="indiareadsTM")
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean(name="indiareadsEM")
	@DependsOn("indiareadsDataStore")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(PROPERTY_PACKAGES_TO_SCAN);

		Properties jpaProperties = new Properties();
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_NAMING_STRATEGY));
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_LAZY_LOAD, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_LAZY_LOAD));
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_ENVERS_AUDIT_TABLE_SUFFIX, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_ENVERS_AUDIT_TABLE_SUFFIX));
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_ENVERS_REVISION_FIELD_NAME, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_ENVERS_REVISION_FIELD_NAME));
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_ENVERS_REVISION_TYPE_FIELD_NAME, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_ENVERS_REVISION_TYPE_FIELD_NAME));
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_ENVERS_REVISION_ON_COLLECTION_CHANGE, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_ENVERS_REVISION_ON_COLLECTION_CHANGE));
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_ENVERS_AUDIT_STRATEGY, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_ENVERS_AUDIT_STRATEGY));
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_ENVERS_AUDIT_STRATEGY_VALIDITY_END_REV_FIELD_NAME, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_ENVERS_AUDIT_STRATEGY_VALIDITY_END_REV_FIELD_NAME));
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_ENVERS_AUDIT_STRATEGY_VALIDITY_STORE_REVEND_TIMESTAMP, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_ENVERS_AUDIT_STRATEGY_VALIDITY_STORE_REVEND_TIMESTAMP));
		jpaProperties.put(PROPERTY_NAME_HIBERNATE_ENVERS_AUDIT_STRATEGY_VALIDITY_REVEND_TIMESTAMP_FIELD_NAME, environment.getRequiredProperty(PROPERTY_NAME_HIBERNATE_ENVERS_AUDIT_STRATEGY_VALIDITY_REVEND_TIMESTAMP_FIELD_NAME));

		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		return entityManagerFactoryBean;
	}
}
