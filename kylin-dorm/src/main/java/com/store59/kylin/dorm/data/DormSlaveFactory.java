package com.store59.kylin.dorm.data;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.store59.kylin.datasource.factory.SlaveDB;
import com.store59.kylin.dorm.data.mapper.DormMapper;
import com.store59.kylin.dorm.data.mapper.DormentryMapper;
import com.store59.kylin.dorm.data.mapper.DormitemMapper;
import com.store59.kylin.dorm.data.mapper.DormpushmapMapper;

@Configuration
public class DormSlaveFactory {
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

	@Bean
	public MapperFactoryBean<DormMapper> slaveDormMapper() throws Exception {
		MapperFactoryBean<DormMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(DormMapper.class);
		mapperFactory.setSqlSessionTemplate(slaveSqlSession());
		return mapperFactory;
	}

	@Bean
	public MapperFactoryBean<DormitemMapper> slaveDormitemMapper()
			throws Exception {
		MapperFactoryBean<DormitemMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(DormitemMapper.class);
		mapperFactory.setSqlSessionTemplate(slaveSqlSession());
		return mapperFactory;
	}
	@Bean
	public MapperFactoryBean<DormpushmapMapper> slaveDormpushmapMapper()
			throws Exception {
		MapperFactoryBean<DormpushmapMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(DormpushmapMapper.class);
		mapperFactory.setSqlSessionTemplate(slaveSqlSession());
		return mapperFactory;
	}

}
