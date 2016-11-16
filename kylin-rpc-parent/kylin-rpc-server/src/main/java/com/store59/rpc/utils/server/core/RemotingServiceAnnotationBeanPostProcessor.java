package com.store59.rpc.utils.server.core;

import java.util.HashMap;
import java.util.Map;

import com.store59.kylin.rpc.context.annotation.Remote;
import com.store59.rpc.utils.server.remoting.HessianServiceExporter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import com.store59.rpc.utils.server.annotation.RemoteService;
import com.store59.rpc.utils.server.config.RemotingServiceConf;
import com.store59.rpc.utils.server.interceptor.RemoteInvocationExceptionInterceptor;

/**
 * Created by shanren on 7/16/15.
 */
@Component
public class RemotingServiceAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements BeanFactoryAware, PriorityOrdered {

    private int                        order;
    private DefaultListableBeanFactory beanFactory;
    private SimpleUrlHandlerMapping    remotingServiceHandlerMapping;
    private final static String EXPORT_METHOD_NAME_SERVICE_INTERFACE = "serviceInterface";
    private final static String EXPORT_METHOD_NAME_SERVICE           = "service";
    private final static String EXPORT_SERVICE_BEAN_NAME_SUFFIX      = "Exporter";

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        Remote remoteAnnotation = AnnotationUtils.findAnnotation(bean.getClass(), Remote.class);

        if (remoteAnnotation != null) {

            Class interfaceClass = null;
            for (Class iclazz : bean.getClass().getInterfaces()) {
                if (iclazz.getDeclaredAnnotation(Remote.class) != null) {
                    interfaceClass = iclazz;
                    break;
                }
            }

            if (interfaceClass == null) {
                throw new FatalBeanException("Exception initializing remoting service for " + beanName + " @Remote should define on the interface.");
            }

            if (StringUtils.isNotEmpty(remoteAnnotation.path()) && !remoteAnnotation.path().startsWith("/")) {
                throw new FatalBeanException("Exception initializing remoting service for " + interfaceClass + " @Remote should bean start with \"/\".");
            }

            String exportPath = remoteAnnotation.path();
            if (StringUtils.isEmpty(exportPath)) {
                exportPath = interfaceClass.getName();
            }
            registerExportBean(beanName, interfaceClass, exportPath);

        } else {
            // 兼容历史注解@RemoteService
            RemoteService service = AnnotationUtils.findAnnotation(bean.getClass(), RemoteService.class);
            processRemoteService(service, beanName);
        }

        return bean;
    }

    // 过期
    private void processRemoteService(RemoteService service, String beanName) {
        if (null != service) {
            if (null == service.exportPath()) {
                throw new FatalBeanException("Exception initializing remoting service for " + beanName + " @RemotingService must has propertie with exportPath.");
            }

            if (!service.exportPath().startsWith("/")) {
                throw new FatalBeanException("Exception initializing remoting service for " + beanName + " @RemotingService should bean start with \"/\".");
            }

            registerExportBean(beanName, service.serviceInterface(), service.exportPath());
        }
    }

    private void registerExportBean(String beanName, Class interfaceClazz, String exportPath) {
        Class<? extends RemoteExporter> remoteExporterclass = HessianServiceExporter.class;
        String exportBeanName = beanName + EXPORT_SERVICE_BEAN_NAME_SUFFIX;
        // 通过BeanDefinitionBuilder创建bean定义
        BeanDefinitionBuilder exportDeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(remoteExporterclass);
        exportDeanDefinitionBuilder.addPropertyValue(EXPORT_METHOD_NAME_SERVICE_INTERFACE, interfaceClazz);
        exportDeanDefinitionBuilder.addPropertyReference(EXPORT_METHOD_NAME_SERVICE, beanName);
        exportDeanDefinitionBuilder.addPropertyValue("interceptors",
                new Object[]{new RemoteInvocationExceptionInterceptor(ClassUtils.getShortName(remoteExporterclass)),
                        beanFactory.getBean("remoteInvocationMonitorInterceptor")});
        beanFactory.registerBeanDefinition(exportBeanName, exportDeanDefinitionBuilder.getBeanDefinition());

        Map<String, Object> umap = new HashMap<String, Object>();
        umap.put(exportPath, beanFactory.getBean(exportBeanName));
        remotingServiceHandlerMapping.setUrlMap(umap);
        remotingServiceHandlerMapping.initApplicationContext();
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
        this.remotingServiceHandlerMapping = (SimpleUrlHandlerMapping) beanFactory.getBean(RemotingServiceConf.REMOTING_SERVICE_HANDLER_MAPPING_NAME);
    }
}
