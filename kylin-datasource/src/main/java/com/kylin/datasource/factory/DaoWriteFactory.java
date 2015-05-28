package com.kylin.datasource.factory;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;

@Component
public class DaoWriteFactory {

	private SqlSessionFactory sqlSessionFactory;

	public DaoWriteFactory() {
		String resource = "com/kylin/datasource/factory/WriteSqlMapConfig.xml";
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			throw new RuntimeException("Fatal: can't load WriteSqlMapConfig.xml");
		}
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
}
