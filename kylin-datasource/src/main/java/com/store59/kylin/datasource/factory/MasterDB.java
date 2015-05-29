package com.store59.kylin.datasource.factory;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MasterDB {
	private String url = "jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=utf8";
	private String driver = "com.mysql.jdbc.Driver";
	private DataSource dataSource;
	private SqlSessionFactoryBean sqlSessionFactory;
	private SqlSessionTemplate sqlSession;

	@Autowired
	public MasterDB(
			@Value("${datasource.master.host}") String host,
			@Value("${datasource.master.port}") int port,
			@Value("${datasource.master.db}") String db,
			@Value("${datasource.master.username}") String username,
			@Value("${datasource.master.password}") String password,
			@Value("${datasource.master.maxconn}") int poolMaximumActiveConnections,
			@Value("${datasource.master.minconn}") int poolMaximumIdleConnections)
			throws Exception {
		PooledDataSource dataSource = new PooledDataSource(this.driver,
				String.format(this.url, host, port, db), username, password);
		dataSource
				.setPoolMaximumActiveConnections(poolMaximumActiveConnections);
		dataSource.setPoolMaximumIdleConnections(poolMaximumIdleConnections);
		dataSource.setPoolPingEnabled(true);
		dataSource.setPoolPingQuery("select 1");
		dataSource.setPoolPingConnectionsNotUsedFor(3600000);
		this.dataSource = dataSource;

		this.sqlSessionFactory = new SqlSessionFactoryBean();
		this.sqlSessionFactory.setDataSource(this.dataSource);

		this.sqlSession = new SqlSessionTemplate(
				this.sqlSessionFactory.getObject());
	}

	public SqlSessionTemplate getSqlSession() {
		return this.sqlSession;
	}
}
