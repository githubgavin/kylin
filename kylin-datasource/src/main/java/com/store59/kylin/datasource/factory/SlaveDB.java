package com.store59.kylin.datasource.factory;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
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
	@Value("${datasource.slave.host}")
	private String host;
	@Value("${datasource.slave.port}")
	private int port;
	@Value("${datasource.slave.db}")
	private String db;
	@Value("${datasource.slave.username}")
	private String username;
	@Value("${datasource.slave.password}")
	private String password;
	@Value("${datasource.slave.maxconn}")
	private int maxActive;
	@Value("${datasource.slave.minconn}")
	private int minIdle;
	@Value("${datasource.slave.maxIdle:0}")
	private int maxIdle;
	@Value("${datasource.slave.validationInterval:30000}")
	private int validationInterval;
	@Value("${datasource.slave.validationQueryTimeout:5}")
	private int validationQueryTimeout;
	@Value("${datasource.slave.timeBetweenEvictionRunsMillis:30000}")
	private int timeBetweenEvictionRunsMillis;
	@Value("${datasource.slave.initialSize:0}")
	private int initialSize;
	@Value("${datasource.slave.maxWait:10000}")
	private int maxWait;
	@Value("${datasource.slave.removeAbandonedTimeout:60}")
	private int removeAbandonedTimeout;
	@Value("${datasource.slave.minEvictableIdleTimeMillis:30000}")
	private int minEvictableIdleTimeMillis;

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
		PoolProperties p = new PoolProperties();
		p.setUrl(String.format(this.url, host, port, db));
		p.setDriverClassName(this.driver);
		p.setUsername(this.username);
		p.setPassword(this.password);
		p.setTestWhileIdle(true);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setValidationInterval(validationInterval);
		p.setValidationQueryTimeout(validationQueryTimeout);
		p.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		p.setMaxActive(maxActive);
		p.setInitialSize(initialSize);
		p.setMaxWait(maxWait);
		p.setRemoveAbandonedTimeout(removeAbandonedTimeout);
		p.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		p.setInitSQL("set names utf8mb4");
		p.setMinIdle(minIdle);
		p.setMaxIdle(maxIdle);
		p.setDefaultReadOnly(true);
		p.setLogAbandoned(true);
		DataSource datasource = new DataSource();
		datasource.setPoolProperties(p);

		DataSource dataSource = new DataSource(p);
		this.dataSource = dataSource;
		this.sqlSessionFactory = new SqlSessionFactoryBean();
		this.sqlSessionFactory.setDataSource(this.dataSource);
		this.sqlSession = new SqlSessionTemplate(this.sqlSessionFactory.getObject());
	}
}
