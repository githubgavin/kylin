package com.store59.kylin.location.data;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.store59.kylin.datasource.factory.MasterDB;
import com.store59.kylin.location.data.mapper.ProvinceMapper;

@Configuration
public class LocationMasterFactory {
	@Autowired
	private MasterDB masterDB;

	@Bean
	public SqlSessionTemplate masterSqlSession() {
		return masterDB.getSqlSession();
	}

	@Bean
	public MapperFactoryBean<ProvinceMapper> masterProvinceMapper()
			throws Exception {
		MapperFactoryBean<ProvinceMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(ProvinceMapper.class);
		mapperFactory.setSqlSessionTemplate(masterSqlSession());
		return mapperFactory;
	}
}
