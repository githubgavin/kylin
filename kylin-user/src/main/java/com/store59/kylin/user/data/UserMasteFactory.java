package com.store59.kylin.user.data;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.store59.kylin.datasource.factory.MasterDB;
import com.store59.kylin.user.data.mapper.UserMapper;

@Configuration
public class UserMasteFactory {
	@Autowired
	private MasterDB masterDB;

	@Bean
	public SqlSessionTemplate masterSqlSession() {
		return masterDB.getSqlSession();
	}

	@Bean
	public MapperFactoryBean<UserMapper> masterUserMapper()
			throws Exception {
		MapperFactoryBean<UserMapper> mapperFactory = new MapperFactoryBean<>();
		mapperFactory.setMapperInterface(UserMapper.class);
		mapperFactory.setSqlSessionTemplate(masterSqlSession());
		return mapperFactory;
	}

}
