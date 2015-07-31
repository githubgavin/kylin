package com.store59.kylin.datasource.factory;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Component
@Configuration
@EnableTransactionManagement
public class MasterDB implements TransactionManagementConfigurer, InitializingBean {
	private String url = "jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=utf8";
	private String driver = "com.mysql.jdbc.Driver";
	private DataSource dataSource;
	private SqlSessionFactoryBean sqlSessionFactory;
	private SqlSessionTemplate sqlSession;
	private @Value("${datasource.master.host}") String host;
	private @Value("${datasource.master.port}") int port;
	private @Value("${datasource.master.db}") String db;
	private @Value("${datasource.master.username}") String username;
	private @Value("${datasource.master.password}") String password;
	private @Value("${datasource.master.maxconn}") int poolMaximumActiveConnections;
	private @Value("${datasource.master.minconn}") int poolMaximumIdleConnections;

	public MasterDB() {
	}
	
	public SqlSessionTemplate getSqlSession() {
		return this.sqlSession;
	}

	public SqlSessionFactoryBean getSqlSessionFactory() {return this.sqlSessionFactory;}

	@Bean SqlSessionTemplate masterSqlSessionTemplate() {
		return this.getSqlSession();
	}

	@Bean SqlSessionFactoryBean masterSqlSessionFactoryBean() {
		return this.getSqlSessionFactory();
	}

	@Bean PlatformTransactionManager txManager() {
		return new DataSourceTransactionManager(dataSource);
	}

	@Override
	 public PlatformTransactionManager annotationDrivenTransactionManager() {
		return txManager();
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
