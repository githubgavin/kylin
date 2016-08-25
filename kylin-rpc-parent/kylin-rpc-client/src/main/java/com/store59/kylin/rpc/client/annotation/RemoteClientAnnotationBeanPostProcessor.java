package com.store59.kylin.rpc.client.annotation;

import com.store59.kylin.context.SpringContext;
import com.store59.kylin.rpc.client.utils.ProxyBuilder;
import com.store59.kylin.rpc.context.annotation.Remote;
import com.store59.kylin.rpc.context.annotation.RemoteBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import java.lang.reflect.Field;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1.1 16/11/16
 * @since 2.1.1
 */
@Component
public class RemoteClientAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements BeanFactoryAware, PriorityOrdered {

    private int                        order = PriorityOrdered.HIGHEST_PRECEDENCE;
    private DefaultListableBeanFactory beanFactory;
    private final static String PACKAGE_AND_APPNAME_RELATION_CONFIGURATION_PREFIX = "reference.mapping.";
    private final static String PACKAGE_NAME_PREFIX = "com.store59";
    private final static int VALID_PREFIX_LENGTH = PACKAGE_AND_APPNAME_RELATION_CONFIGURATION_PREFIX.length() + PACKAGE_NAME_PREFIX.length();
    private Environment environment = SpringContext.getEnvironment();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        Configuration annotation = AnnotationUtils.findAnnotation(bean.getClass(), Configuration.class);

        if (annotation != null) {
            Field[] fields = bean.getClass().getSuperclass().getDeclaredFields();
            for (Field field : fields) {
                RemoteBean client = field.getDeclaredAnnotation(RemoteBean.class);
                if (client != null) {

                    String exportPath = client.path();
                    long connectTimeout = client.connectTimeout();
                    long readTimeout = client.readTimeout();
                    Class interfaceClass = field.getType();
                    String clientBeanName = field.getName();
                    String appName = findAppName(interfaceClass);

                    if (StringUtils.isEmpty(appName)) {
                        throw new FatalBeanException("Exception initializing @RemoteBean for " + clientBeanName + ", application name did not find in configserver.");
                    }

                    if (!interfaceClass.isInterface()) {
                        throw new FatalBeanException("Exception initializing @RemoteBean for " + clientBeanName + " must be a interface.");
                    }

                    Remote remote = AnnotationUtils.findAnnotation(interfaceClass, Remote.class);
                    if (remote == null) {
                        // 如果注册的接口上没有@Remote, 则必须在@RemoteClient配置path
                        if (StringUtils.isEmpty(exportPath)) {
                            throw new FatalBeanException("Exception initializing remoting client for " + beanName + " @RemoteClient.path must not be empty when" +
                                    " interface " + interfaceClass + " without @Remote. ");
                        }
                    } else {
                        // 如果注册接口上有@Remote注解, 则属性按照@Remote来配置
                        if (StringUtils.isNotEmpty(remote.path())) {
                            exportPath = remote.path();
                        } else {
                            // 默认接口的全路径
                            exportPath = interfaceClass.getName();
                        }
                        if (connectTimeout <= 0 && remote.connectTimeout() > 0) {
                            connectTimeout = remote.connectTimeout();
                        }
                        if (readTimeout <= 0 && remote.readTimeout() > 0) {
                            readTimeout = remote.readTimeout();
                        }
                    }

                    if (exportPath.startsWith("/")) {
                        exportPath = exportPath.substring(1);
                    }

                    // 暂时允许未写明Remote.class的
                    Object obj = ProxyBuilder.create()
                            .setServiceExportName(exportPath)
                            .setServiceUrl(appName)
                            .setInterfaceClass(interfaceClass)
                            .setConnectTimeout(connectTimeout)
                            .setReadTimeout(readTimeout)
                            .build();
//                    field.set(bean, obj);
                    beanFactory.registerSingleton(clientBeanName, obj);
                }
            }
        }

        return bean;
    }

    private <T> String findAppName(Class<T> tClass) {
        Assert.notNull(environment, "SpringContext.environment must not be null.");
        String packageName = PACKAGE_AND_APPNAME_RELATION_CONFIGURATION_PREFIX + tClass.getName();
        while (packageName.length() > VALID_PREFIX_LENGTH) {
            if (environment.containsProperty(packageName)) {
                return environment.getProperty(packageName);
            }
            packageName = packageName.substring(0, packageName.lastIndexOf("."));
        }
        return null;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }
}
