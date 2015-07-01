package com.store59.kylin.ext.data;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.store59.kylin.datasource.factory.SlaveDB;
import com.store59.kylin.ext.data.mapper.AppVersionMapper;

@Configuration
public class ExtSlaveFactory {
	@Autowired
	private SlaveDB slaveDB;

	@Bean
	public SqlSessionTemplate slaveSqlSession() {
		return slaveDB.getSqlSession();
	}

	@Bean
	public MapperFactoryBean<AppVersionMapper> slaveAppVersionMapper()
			throws Exception {
		MapperFactoryBean<AppVersionMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(AppVersionMapper.class);
		mapperFactory.setSqlSessionTemplate(slaveSqlSession());
		return mapperFactory;
	}

}
