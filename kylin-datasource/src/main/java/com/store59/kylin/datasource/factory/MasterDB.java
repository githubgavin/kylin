package com.store59.kylin.datasource.factory;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
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
    @Value("${datasource.master.host}")
    private String host;
    @Value("${datasource.master.port}")
    private int port;
    @Value("${datasource.master.db}")
    private String db;
    @Value("${datasource.master.username}")
    private String username;
    @Value("${datasource.master.password}")
    private String password;
    @Value("${datasource.master.maxconn}")
    private int maxActive;
    @Value("${datasource.master.minconn}")
    private int minIdle;
    @Value("${datasource.master.maxIdle:0}")
    private int maxIdle;
    @Value("${datasource.master.validationInterval:30000}")
    private int validationInterval;
    @Value("${datasource.master.validationQueryTimeout:5}")
    private int validationQueryTimeout;
    @Value("${datasource.master.timeBetweenEvictionRunsMillis:30000}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${datasource.master.initialSize:0}")
    private int initialSize;
    @Value("${datasource.master.maxWait:10000}")
    private int maxWait;
    @Value("${datasource.master.removeAbandonedTimeout:60}")
    private int removeAbandonedTimeout;
    @Value("${datasource.master.minEvictableIdleTimeMillis:30000}")
    private int minEvictableIdleTimeMillis;

    public MasterDB() {
    }

    public SqlSessionTemplate getSqlSession() {
        return this.sqlSession;
    }

    public SqlSessionFactoryBean getSqlSessionFactory() {
        return this.sqlSessionFactory;
    }

    @Bean
    @Scope("prototype")
    SqlSessionTemplate masterSqlSessionTemplate() {
        return this.getSqlSession();
    }

    @Bean
    SqlSessionFactoryBean masterSqlSessionFactoryBean() {
        return this.getSqlSessionFactory();
    }

    @Bean
    PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (maxIdle <= 0) {
            maxIdle = maxActive;
        }
        if (initialSize <= 0) {
            initialSize = minIdle;
        }
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
        p.setLogAbandoned(true);
        p.setDefaultAutoCommit(true);
        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);

        DataSource dataSource = new DataSource(p);
        this.dataSource = dataSource;
        this.sqlSessionFactory = new SqlSessionFactoryBean();
        this.sqlSessionFactory.setDataSource(this.dataSource);
        this.sqlSession = new SqlSessionTemplate(this.sqlSessionFactory.getObject());
    }
}
