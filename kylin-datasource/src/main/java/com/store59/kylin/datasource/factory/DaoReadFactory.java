package com.store59.kylin.datasource.factory;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;

@Component
public class DaoReadFactory {

	private SqlSessionFactory sqlSessionFactory;

	public DaoReadFactory() {
		String resource = "com/kylin/datasource/factory/ReadSqlMapConfig.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			throw new RuntimeException("Fatal: can't load ReadSqlMapConfig.xml");
		}
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
}
