package com.store59.kylin.dorm.data;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.store59.kylin.datasource.factory.MasterDB;
import com.store59.kylin.dorm.data.mapper.DormentryMapper;

@Configuration
public class MasterMapperFactory {
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

}
