package se.leanbit.ticketsystem.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;

@Configuration
@EnableJpaRepositories("se.leanbit.ticketsystem.repository")
@EnableTransactionManagement
public class InfraStructureConfig
{

	@Bean
	public DataSource dataSource()
	{
		HikariConfig config = new HikariConfig();
		//config.setDriverClassName("org.h2.Driver");
		//config.setJdbcUrl("jdbc:h2:mem:datajpa");
		//config.setUsername("root");
		//config.setPassword("");

		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setJdbcUrl("jdbc:mysql://localhost/Leanbit_Ticket_System?characterEncoding=utf8");
		config.setUsername("leanbit");
		config.setPassword("theone92");
		config.setMaximumPoolSize(10);
		config.setMinimumIdle(2);
		return new HikariDataSource(config);
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory factory)
	{
		return new JpaTransactionManager(factory);
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter()
	{
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		//adapter.setDatabase(Database.H2);
		adapter.setDatabase(Database.MYSQL);
		adapter.setGenerateDdl(true);

		return adapter;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment env)
	{
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factory.setPackagesToScan("se.leanbit.ticketsystem.model");
		factory.setJpaProperties(entityManagerProperties());

		return factory;
	}

	Properties entityManagerProperties()
	{
		return new Properties()
		{
			{
				//Should re reset the database each time we open a connection?
				//setProperty("hibernate.hbm2ddl.auto", "create");
			}
		};
	}

}