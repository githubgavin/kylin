/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.client.annotation;

import com.store59.kylin.rpc.context.annotation.RemoteConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;

/**
 *
 * 此方案搁浅, 由于BeanDefinition注册的是Interface, 无法顺利初始化, 暂时使用延迟加载方案
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1.1 16/8/25
 * @since 2.1.1
 */
//@Component
public class RemoteClientBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor,
        PriorityOrdered {

    private static Logger logger = LoggerFactory.getLogger(RemoteClientBeanDefinitionRegistryPostProcessor.class);

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        logger.info("RemoteClientBeanDefinitionRegistryPostProcessor postProcessBeanDefinitionRegistry ");
//        for (String beanName : registry.getBeanDefinitionNames()) {
//            BeanDefinition beanDef = registry.getBeanDefinition(beanName);
//            if (!(beanDef instanceof AnnotatedBeanDefinition)) {
//                continue;
//            }
//            AnnotatedBeanDefinition annoBeanDef = (AnnotatedBeanDefinition) beanDef;
//            if (annoBeanDef.getMetadata().hasAnnotation(RemoteConfiguration.class.getName())) {
//                Class clientClass = null;
//                try {
//                    clientClass = ClassUtils.getDefaultClassLoader().loadClass(annoBeanDef.getBeanClassName());
//                } catch (ClassNotFoundException e) {
//                    logger.error(e.getMessage(), e);
//                    continue;
//                }
//                Field[] fields = clientClass.getDeclaredFields();
//                for (Field field : fields) {
//                    RemoteClient client = field.getDeclaredAnnotation(RemoteClient.class);
//                    if (client != null) {
//                        Class interfaceClass = field.getType();
//                        String clientBeanName = field.getName();
//
//                        registry.registerBeanDefinition(clientBeanName, new RootBeanDefinition(interfaceClass));
//                    }
//                }
//            }
//        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        logger.info("RemoteClientBeanDefinitionRegistryPostProcessor postProcessBeanFactory ");
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.LOWEST_PRECEDENCE;
    }

}
