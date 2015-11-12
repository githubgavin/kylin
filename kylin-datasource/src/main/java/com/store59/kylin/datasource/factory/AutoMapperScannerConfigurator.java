/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.kylin.datasource.factory;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.util.Properties;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 15/11/12
 * @since 1.0
 */
@Configuration
@Component
public class AutoMapperScannerConfigurator {

    @Bean
    Properties datasourceProperties() throws Exception {
        PropertiesFactoryBean factoryBean = new PropertiesFactoryBean();
        factoryBean.setLocation(new ClassPathResource("datasource.properties"));
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Bean
    MapperScannerConfigurer masterMapperScannerConfigurer() throws Exception {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage(datasourceProperties().getProperty("datasource.master.mappersPath"));
        configurer.setSqlSessionTemplateBeanName("masterSqlSessionTemplate");
        configurer.setNameGenerator(new MapperBeanNameGenerator("master"));
        return configurer;
    }

    @Bean
    MapperScannerConfigurer slaveMapperScannerConfigurer() throws Exception {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage(datasourceProperties().getProperty("datasource.slave.mappersPath"));
        configurer.setSqlSessionTemplateBeanName("slaveSqlSessionTemplate");
        configurer.setNameGenerator(new MapperBeanNameGenerator("slave"));
        return configurer;
    }

}
