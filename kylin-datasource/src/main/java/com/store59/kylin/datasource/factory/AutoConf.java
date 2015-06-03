package com.store59.kylin.datasource.factory;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConf {
	@Autowired
	private MasterDB masterDB;

	@Bean
	public DataSource defaultDataSource() {
		return masterDB.getDataSource();
	}
}
