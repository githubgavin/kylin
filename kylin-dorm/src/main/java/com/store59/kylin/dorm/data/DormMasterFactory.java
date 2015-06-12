package com.store59.kylin.dorm.data;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.store59.kylin.datasource.factory.MasterDB;
import com.store59.kylin.dorm.data.mapper.DormMapper;
import com.store59.kylin.dorm.data.mapper.DormentryMapper;
import com.store59.kylin.dorm.data.mapper.DormitemMapper;
import com.store59.kylin.dorm.data.mapper.DormpushmapMapper;

@Configuration
public class DormMasterFactory {
	@Autowired
	private MasterDB masterDB;

	@Bean
	public SqlSessionTemplate masterSqlSession() {
		return masterDB.getSqlSession();
	}

	@Bean
	public MapperFactoryBean<DormentryMapper> masterDormentryMapper()
			throws Exception {
		MapperFactoryBean<DormentryMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(DormentryMapper.class);
		mapperFactory.setSqlSessionTemplate(masterSqlSession());
		return mapperFactory;
	}

	@Bean
	public MapperFactoryBean<DormMapper> masterDormMapper() throws Exception {
		MapperFactoryBean<DormMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(DormMapper.class);
		mapperFactory.setSqlSessionTemplate(masterSqlSession());
		return mapperFactory;
	}

	@Bean
	public MapperFactoryBean<DormitemMapper> masterDormitemMapper()
			throws Exception {
		MapperFactoryBean<DormitemMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(DormitemMapper.class);
		mapperFactory.setSqlSessionTemplate(masterSqlSession());
		return mapperFactory;
	}
	
	@Bean
	public MapperFactoryBean<DormpushmapMapper> masterDormpushmapMapper()
			throws Exception {
		MapperFactoryBean<DormpushmapMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(DormpushmapMapper.class);
		mapperFactory.setSqlSessionTemplate(masterSqlSession());
		return mapperFactory;
	}
}
