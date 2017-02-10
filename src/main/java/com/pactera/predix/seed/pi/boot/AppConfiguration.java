package com.pactera.predix.seed.pi.boot;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
public class AppConfiguration{
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		
		//dataSource.setUrl("jdbc:postgres://u328edf88d4fc43689ef2d170e84d887c:93c309d1c8454c35afbea0c2bb68fa50@10.72.6.134:5432/d328edf88d4fc43689ef2d170e84d887c?sslmode=disable");
		//dataSource.setUsername("u328edf88d4fc43689ef2d170e84d887c");
		//dataSource.setPassword("93c309d1c8454c35afbea0c2bb68fa50");
		
		dataSource.setUrl("jdbc:postgresql://10.72.6.143:5432/d577d4f17e6db47eebd48ec67db31f2bf?user=ueac619d7d79b4c01b842c23574cb8ad3\u0026password=93390e226aec4537bc922db07e98b4e7\u0026ssl=false");
		dataSource.setUsername("ueac619d7d79b4c01b842c23574cb8ad3");
		dataSource.setPassword("93390e226aec4537bc922db07e98b4e7");
		
		return dataSource;
	}
		
}


