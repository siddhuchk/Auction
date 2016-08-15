package com.enterprise.adapter.spring.root;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.enterprise.adapter.utilities.PropertyKeyConstants;
import com.jolbox.bonecp.BoneCPDataSource;
import com.jolbox.bonecp.ConnectionHandle;
import com.jolbox.bonecp.hooks.AbstractConnectionHook;
import com.jolbox.bonecp.hooks.AcquireFailConfig;

/**
 * 
 * @author anuj.kumar2
 *
 */
@Configuration(value = "domainPersistenceConfig")
@EnableTransactionManagement
@PropertySource(value = { "classpath:Configuration/persistence.properties",
		"classpath:Configuration/application.properties"
		// "file:${SMP_CONFIG_PATH}/application.properties"
})
public class PersistenceConfig {

	@Autowired
	private Environment environment;

	@Bean
	public DataSource dataSource() {
		BoneCPDataSource dataSource = new BoneCPDataSource();

		dataSource.setDriverClass(environment
				.getRequiredProperty(PropertyKeyConstants.PROPERTY_NAME_JDBC_DRIVER_CLASSNAME.getKeyConstants()));
		dataSource.setJdbcUrl(
				environment.getRequiredProperty(PropertyKeyConstants.PROPERTY_NAME_JDBC_URL.getKeyConstants()));
		dataSource.setUsername(
				environment.getRequiredProperty(PropertyKeyConstants.PROPERTY_NAME_JDBC_USERNAME.getKeyConstants()));
		dataSource.setPassword(
				environment.getRequiredProperty(PropertyKeyConstants.PROPERTY_NAME_JDBC_PASSWORD.getKeyConstants()));

		dataSource.setMaxConnectionAgeInSeconds(Integer.parseInt(
				environment.getRequiredProperty(PropertyKeyConstants.MAX_CONNECTIONAGE_IN_SECONDS.getKeyConstants())));
		dataSource.setConnectionTimeout(
				Integer.parseInt(environment
						.getRequiredProperty(PropertyKeyConstants.MAX_CONNECTIONAGE_IN_SECONDS.getKeyConstants())),
				TimeUnit.SECONDS);
		dataSource.setConnectionHook(new AbstractConnectionHook() {
			@Override
			public boolean onConnectionException(ConnectionHandle connection, String state, Throwable t) {
				return super.onConnectionException(connection, state, t);
			}

			@Override
			public boolean onAcquireFail(Throwable t, AcquireFailConfig acquireConfig) {
				return super.onAcquireFail(t, acquireConfig);
			}
		});

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

		factoryBean.setDataSource(dataSource());
		factoryBean
				.setPersistenceUnitName(PropertyKeyConstants.PROPERTY_NAME_U2OPIA_NOTIFY_WEBSERVICES.getKeyConstants());
		factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter());
		factoryBean.setJpaDialect(hibernateIsolationJpaDialect());
		factoryBean.setJpaPropertyMap(jpaPropertyMap());

		return factoryBean;
	}

	@Bean(name = "transactionManager")
	public JpaTransactionManager transactionManager() {
		final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();

		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		jpaTransactionManager.setDataSource(dataSource());

		return jpaTransactionManager;
	}

	@Bean
	public HibernateIsolationJpaDialect hibernateIsolationJpaDialect() {
		return new HibernateIsolationJpaDialect();
	}

	@Bean
	public HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
		final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		jpaVendorAdapter.setShowSql(Boolean.parseBoolean(
				environment.getProperty(PropertyKeyConstants.PROPERTY_NAME_JDBC_SHOW_SQL.getKeyConstants())));
		jpaVendorAdapter.setGenerateDdl(Boolean.parseBoolean(
				environment.getProperty(PropertyKeyConstants.PROPERTY_NAME_JDBC_GENERATE_DDL.getKeyConstants())));
		jpaVendorAdapter.setDatabase(Database
				.valueOf(environment.getProperty(PropertyKeyConstants.PROPERTY_NAME_JDBC_DATABASE.getKeyConstants())));
		String databasePlatform = environment
				.getProperty(PropertyKeyConstants.PROPERTY_NAME_JDBC_DATABASE_PLATFORM.getKeyConstants());
		if (databasePlatform != null) {
			jpaVendorAdapter.setDatabasePlatform(environment
					.getProperty(PropertyKeyConstants.PROPERTY_NAME_JDBC_DATABASE_PLATFORM1.getKeyConstants()));
		}

		return jpaVendorAdapter;
	}

	@Bean(name = "jpaPropertyMap")
	public Map<String, Object> jpaPropertyMap() {
		Map<String, Object> map = new HashMap<>();
		map.put(PropertyKeyConstants.PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE.getKeyConstants(),
				Integer.parseInt(environment
						.getProperty(PropertyKeyConstants.PROPERTY_NAME_HIBERNATE_JDBC_BATCH_SIZE.getKeyConstants())));
		return map;
	}
}