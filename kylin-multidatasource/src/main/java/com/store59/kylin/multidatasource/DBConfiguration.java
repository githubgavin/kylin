/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.multidatasource;

import com.store59.kylin.multidatasource.db.DBLink;
import com.store59.kylin.multidatasource.factory.DBFactory;
import com.store59.kylin.multidatasource.mybatis.scanner.MapperAutoScannerConfigurer;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据库启动配置类
 *
 * @author <a href="mailto:zhongc@59store.com">士兵</a>
 * @version 1.0 16/8/7
 * @since 1.0
 */
@Component
@EnableTransactionManagement
public class DBConfiguration implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private final static Logger logger = LoggerFactory.getLogger(DBConfiguration.class);

    private ApplicationContext applicationContext;

    private DBFactory dbFactory;

    private List<DBLink> dbLinks;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        dbFactory = new DBFactory((DefaultListableBeanFactory) beanFactory);

        dbLinks.stream().forEach(db -> {
            //创建datasource
            dbFactory.registerDataSource(db);

            //创建sqlSessionFactoryBean
            dbFactory.registerSqlSessionFactoryBean(db.getKey());

            //创建sqlSessionTemplate
            dbFactory.registerSqlSessionTemplate(db.getKey());

            //创建事务transactionManager
            if(null == db.getTransactionAble()) {
                logger.error("transactionAble must not be null");
                throw new RuntimeException("transactionAble must not be null");
            } else if(db.getTransactionAble()) {
                dbFactory.registerTransactionManager(db.getKey());
            }

            //创建mybatis扫描
            MapperAutoScannerConfigurer.scan(applicationContext, (DefaultListableBeanFactory) beanFactory,
                    db.getKey(), db.getMapperPath());
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
//拿到spring.datasource的属性解析器
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment,"spring.datasource.");

        //将属性转换成map, 病获得 dbLinks[n]的set集合
        Map<String, Object> properties = propertyResolver.getSubProperties("");
        Set<String> dbLinksKey = new HashSet<>();

        //e.split("\\.")[0] = dbLink[n]
        properties.keySet().forEach(e -> dbLinksKey.add(e.split("\\.")[0]));

        this.dbLinks = dbLinksKey.parallelStream().map(e -> {
            Map<String, Object> dbLinkMap = propertyResolver.getSubProperties(e +".");
            DBLink dbLink = new DBLink();
            try {
                BeanUtils.populate(dbLink, dbLinkMap);
            } catch (Exception ex) {
                logger.error("spring.datasource convert error");
                throw new RuntimeException("spring.datasource.dbLinks is error");
            }
            return dbLink;
        }).collect(Collectors.toList());
    }
}
