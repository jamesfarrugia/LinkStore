package com.jf.java.web.ls;

import javax.sql.DataSource;

import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jf.java.web.ls.repository.jooq.JooqExceptionTransformer;
import com.jolbox.bonecp.BoneCPDataSource;

/**
 * Configuration class for the spring web app.
 * 
 * @author jfarrugia
 *
 */
@EnableWebMvc
@Configuration
@ComponentScan({ "com.jf.java.web.ls" })
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class SpringWebConfig extends WebMvcConfigurerAdapter 
{
	@Autowired private Environment env;

	/**
	 * Loads the datasource with the application properties.
	 * 
	 * @return the application properties
	 */
	@Bean(destroyMethod = "close")
	public DataSource dataSource() 
	{
		BoneCPDataSource dataSource = new BoneCPDataSource();

		dataSource.setDriverClass(env.getRequiredProperty("db.driver"));
		dataSource.setJdbcUrl(env.getRequiredProperty("db.url"));

		return dataSource;
	}
	
	/**
	 * Create a new Lazy data source based on the original data source.
	 * 
	 * @return a lazy data source
	 */
	@Bean
	public LazyConnectionDataSourceProxy lazyConnectionDataSource()
	{
		return new LazyConnectionDataSourceProxy(dataSource());
	}

	/**
	 * Creates a transaction aware data source based on the lazy data source.
	 * 
	 * @return a transaction aware data source
	 */
	@Bean
	public TransactionAwareDataSourceProxy transactionAwareDataSource()
	{
		return new TransactionAwareDataSourceProxy(lazyConnectionDataSource());
	}

	/**
	 * Creates a transaction manager based on the lazy data source.
	 * 
	 * @return a transaction manager
	 */
	@Bean
	public DataSourceTransactionManager transactionManager()
	{
		return new DataSourceTransactionManager(lazyConnectionDataSource());
	}

	/**
	 * Creates a connection provider based on the transaction aware data source.
	 * 
	 * @return a connection provider
	 */
	@Bean
	public DataSourceConnectionProvider connectionProvider()
	{
		return new DataSourceConnectionProvider(transactionAwareDataSource());
	}

	/**
	 * Creates an instance of the jOOQ to Spring exception transformer.
	 * 
	 * @return an exception transformer
	 */
	@Bean
	public JooqExceptionTransformer jooqExceptionTransformer()
	{
		return new JooqExceptionTransformer();
	}

	/**
	 * Creates the configuration for the database.
	 * 
	 * @return a configuration
	 */
	@Bean
	public DefaultConfiguration configuration()
	{
		DefaultConfiguration jooqConfiguration = new DefaultConfiguration();

		jooqConfiguration.set(connectionProvider());
		jooqConfiguration.set(new DefaultExecuteListenerProvider(
				jooqExceptionTransformer()));

		String sqlDialectName = env.getRequiredProperty("jooq.sql.dialect");
		SQLDialect dialect = SQLDialect.valueOf(sqlDialectName);
		jooqConfiguration.set(dialect);

		return jooqConfiguration;
	}

	/**
	 * Crates the jOOQ DSL context to be used for DB operations.
	 * 
	 * @return the jOOQ DSL
	 */
	@Bean
	public DefaultDSLContext dsl()
	{
		return new DefaultDSLContext(configuration());
	}

	/**
	 * Creates the datasource initialiser.
	 * 
	 * @return an initiaser for the datasources
	 */
	@Bean
	public DataSourceInitializer dataSourceInitializer()
	{
		DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource());

		return initializer;
	}
}