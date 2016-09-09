/*
 * Copyright 2016 © 59store.com.
 *
 * Remote.java
 *
 */
package com.store59.kylin.rpc.context.annotation;

import java.lang.annotation.*;

/**
 * 远程服务client配置注解, 用于自动配置RPC client
 * 服务名无需配置, 会自动从配置中心获取包路径映射服务名
 *
 * 使用方法详见 {@link RemoteConfiguration}
 *
 * 解析详见 {@link RemoteClientAnnotationBeanPostProcessor}
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1.1 16/11/16
 * @since 2.1.1
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
public @interface RemoteBean {

    /**
     * 超时时间
     */
    long connectTimeout() default -1;

    /**
     * 超时时间
     */
    long readTimeout() default -1;

    /**
     * 如果接口定义了@Remote注解, 会自动取@Remote定义的值, 该属性无需配置
     * 否则需要手工配置成, 保持和@RemoteService中的exportPath一致
     *
     * 远程服务暴露的path
     */
    String path() default "";

}
