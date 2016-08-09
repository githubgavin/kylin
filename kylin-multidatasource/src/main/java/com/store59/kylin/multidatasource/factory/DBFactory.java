/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.multidatasource.factory;

import com.alibaba.druid.pool.DruidDataSource;
import com.store59.kylin.multidatasource.db.DBLink;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * DB工厂类
 *
 * @author <a href="mailto:zhongc@59store.com">士兵</a>
 * @version 1.0 16/7/14
 * @since 1.0
 */
public class DBFactory {

    private DefaultListableBeanFactory beanFactory;

    public DBFactory(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 注册datasource
     *
     * @param dbLink
     */
    public void registerDataSource(DBLink dbLink) {
        DruidDataSource dataSource = DBHelper.buildDataSource(dbLink);
        beanFactory.registerSingleton(dbLink.getKey() + "DataSource", dataSource);
    }

    /**
     * 注册SqlSessionFactoryBean
     */
    public void registerSqlSessionFactoryBean(String key) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource((DruidDataSource) beanFactory.getBean(key + "DataSource"));
        beanFactory.registerSingleton(key + "SqlSessionFactoryBean", sqlSessionFactoryBean);
    }

    /**
     * 注册SqlSessionTemplate
     */
    public void registerSqlSessionTemplate(String key) {
        DefaultSqlSessionFactory sqlSessionFactory = (DefaultSqlSessionFactory) beanFactory.getBean(key + "SqlSessionFactoryBean");
        beanFactory.registerSingleton(key + "SqlSessionTemplate", new SqlSessionTemplate(sqlSessionFactory));
    }

    /**
     * 注册TransactionManager
     */
    public void registerTransactionManager(String key) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager((DruidDataSource) beanFactory.getBean(key + "DataSource"));
        beanFactory.registerSingleton(key + "TransactionManager", manager);
    }
}
