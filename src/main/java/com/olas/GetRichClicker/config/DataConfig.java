package com.olas.GetRichClicker.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
public class DataConfig 
{
	private final String PROPERTY_DRIVER = "driver";
	private final String PROPERTY_URL = "url";
	private final String PROPERTY_USERNAME = "user";
	private final String PROPERTY_PASSWORD = "password";
	
	@Autowired
	Environment environment;
	
	@Bean
	DataSource dataSource() 
	{
		// database information for dataSource
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl( environment.getProperty(PROPERTY_URL) );
		dataSource.setUsername( environment.getProperty(PROPERTY_USERNAME) );
		dataSource.setPassword( environment.getProperty(PROPERTY_PASSWORD) );
		dataSource.setDriverClassName( environment.getProperty(PROPERTY_DRIVER) );
		return dataSource;
	}
	
	@Bean
	JdbcTemplate jdbcTemplate()
	{
		// Initalizing jdbcTemplate with our dataSource, which we have above
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource( dataSource() );
		return jdbcTemplate;
	}
}
