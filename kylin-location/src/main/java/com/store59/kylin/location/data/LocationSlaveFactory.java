package com.store59.kylin.location.data;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.store59.kylin.datasource.factory.SlaveDB;
import com.store59.kylin.location.data.mapper.ProvinceMapper;

@Configuration
public class LocationSlaveFactory {
	@Autowired
	private SlaveDB slaveDB;

	@Bean
	public SqlSessionTemplate slaveSqlSession() {
		return slaveDB.getSqlSession();
	}

	@Bean
	public MapperFactoryBean<ProvinceMapper> slaveProvinceMapper()
			throws Exception {
		MapperFactoryBean<ProvinceMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(ProvinceMapper.class);
		mapperFactory.setSqlSessionTemplate(slaveSqlSession());
		return mapperFactory;
	}

}
