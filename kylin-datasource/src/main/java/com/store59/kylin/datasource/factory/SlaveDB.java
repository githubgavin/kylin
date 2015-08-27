package com.store59.kylin.datasource.factory;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class SlaveDB implements InitializingBean {
	private String url = "jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=utf8";
	private String driver = "com.mysql.jdbc.Driver";
	private DataSource dataSource;
	private SqlSessionFactoryBean sqlSessionFactory;
	private SqlSessionTemplate sqlSession;
	private @Value("${datasource.slave.host}") String host;
	private @Value("${datasource.slave.port}") int port;
	private @Value("${datasource.slave.db}") String db;
	private @Value("${datasource.slave.username}") String username;
	private @Value("${datasource.slave.password}") String password;
	private @Value("${datasource.slave.maxconn}") int poolMaximumActiveConnections;
	private @Value("${datasource.slave.minconn}") int poolMaximumIdleConnections;

	@Autowired
	public SlaveDB() {}

	public SqlSessionTemplate getSqlSession() {
		return this.sqlSession;
	}

	public SqlSessionFactoryBean getSqlSessionFactory() {return this.sqlSessionFactory;}

	@Bean
	SqlSessionTemplate slaveSqlSessionTemplate() {
		return this.getSqlSession();
	}

	@Bean SqlSessionFactoryBean slaveSqlSessionFactoryBean() {
		return this.getSqlSessionFactory();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
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
}
