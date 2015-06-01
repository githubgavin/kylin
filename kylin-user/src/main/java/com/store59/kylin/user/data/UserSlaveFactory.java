package com.store59.kylin.user.data;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.store59.kylin.datasource.factory.SlaveDB;
import com.store59.kylin.user.data.mapper.UserMapper;

@Configuration
public class UserSlaveFactory {
	@Autowired
	private SlaveDB slaveDB;

	@Bean
	public SqlSessionTemplate slaveSqlSession() {
		return slaveDB.getSqlSession();
	}

	@Bean
	public MapperFactoryBean<UserMapper> slaveUserMapper()
			throws Exception {
		MapperFactoryBean<UserMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(UserMapper.class);
		mapperFactory.setSqlSessionTemplate(slaveSqlSession());
		return mapperFactory;
	}

}
