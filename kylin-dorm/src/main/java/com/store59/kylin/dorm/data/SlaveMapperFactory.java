package com.store59.kylin.dorm.data;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.store59.kylin.datasource.factory.SlaveDB;
import com.store59.kylin.dorm.data.mapper.DormentryMapper;

@Configuration
public class SlaveMapperFactory {
	@Autowired
	private SlaveDB slaveDB;

	@Bean
	public SqlSessionTemplate slaveSqlSession() {
		return slaveDB.getSqlSession();
	}

	@Bean
	public MapperFactoryBean<DormentryMapper> slaveDormentryMapper()
			throws Exception {
		MapperFactoryBean<DormentryMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(DormentryMapper.class);
		mapperFactory.setSqlSessionTemplate(slaveSqlSession());
		return mapperFactory;
	}

}
