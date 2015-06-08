package com.store59.kylin.order.data;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.store59.kylin.datasource.factory.SlaveDB;
import com.store59.kylin.order.data.mapper.OrderMapper;
import com.store59.kylin.order.data.mapper.OrderfoodMapper;

@Configuration
public class OrderSlaveFactory {
	@Autowired
	private SlaveDB slaveDB;

	@Bean
	public SqlSessionTemplate slaveSqlSession() {
		return slaveDB.getSqlSession();
	}

	@Bean
	public MapperFactoryBean<OrderMapper> slaveOrderMapper() throws Exception {
		MapperFactoryBean<OrderMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(OrderMapper.class);
		mapperFactory.setSqlSessionTemplate(slaveSqlSession());
		return mapperFactory;
	}

	@Bean
	public MapperFactoryBean<OrderfoodMapper> slaveOrderfoodMapper()
			throws Exception {
		MapperFactoryBean<OrderfoodMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(OrderfoodMapper.class);
		mapperFactory.setSqlSessionTemplate(slaveSqlSession());
		return mapperFactory;
	}
}
