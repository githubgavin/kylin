/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.client.utils;

import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 15/11/13
 * @since 1.0
 */
public class HessianProxyUtil {

    /**
     *  方法过时, 请使用ProxyBuilder类创建
     *
     * */
    @Deprecated
    public static Object buildRemotingService(String serviceUrl, String serviceExportName, Class clazz) {
        HessianProxyFactoryBean hessianProxyFactoryBean = new HessianProxyFactoryBean();
        ProxyBuilder.fillProperties(hessianProxyFactoryBean, serviceUrl, serviceExportName, clazz);
        hessianProxyFactoryBean.setConnectTimeout(3000);
        hessianProxyFactoryBean.setReadTimeout(3000);
        hessianProxyFactoryBean.afterPropertiesSet();
        return hessianProxyFactoryBean.getObject();
    }

}
