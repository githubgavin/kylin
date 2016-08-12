/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.multidatasource.mybatis.scanner;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

/**
 * mybatis扫描实现
 *
 * @author <a href="mailto:zhongc@59store.com">士兵</a>
 * @version 1.0 16/7/14
 * @since 1.0
 */
public class MapperAutoScannerConfigurer {

    /**
     * 扫描mybatis
     *
     * @param applicationContext
     * @param beanFactory
     * @param prefix
     * @param mapperPath
     */
    public static void scan(ApplicationContext applicationContext, DefaultListableBeanFactory beanFactory,
                            String prefix, String mapperPath) {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();

        mapperScannerConfigurer.setSqlSessionTemplateBeanName(prefix + "SqlSessionTemplate");
        mapperScannerConfigurer.setBasePackage(mapperPath);
        mapperScannerConfigurer.setNameGenerator(new MapperBeanNameGenerator(prefix));
        mapperScannerConfigurer.setApplicationContext(applicationContext);
        mapperScannerConfigurer.postProcessBeanDefinitionRegistry(beanFactory);
    }
}
