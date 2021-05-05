package com.db.extrato.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {
  
	@Primary
	@Bean
	@ConfigurationProperties(prefix = "extr.datasource")
	public DataSource springDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean
	@ConfigurationProperties(prefix = "deriv.datasource")
	public DataSource derivDataSource() {
		return DataSourceBuilder.create().build();
	}
}
