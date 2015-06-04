package com.store59.kylin.order.data;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.store59.kylin.datasource.factory.MasterDB;
import com.store59.kylin.order.data.mapper.OrderMapper;

@Configuration
public class OrderMasterFactory {
	@Autowired
	private MasterDB masterDB;

	@Bean
	public SqlSessionTemplate masterSqlSession() {
		return masterDB.getSqlSession();
	}

	@Bean
	public MapperFactoryBean<OrderMapper> masterOrderMapper() throws Exception {
		MapperFactoryBean<OrderMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(OrderMapper.class);
		mapperFactory.setSqlSessionTemplate(masterSqlSession());
		return mapperFactory;
	}
}
