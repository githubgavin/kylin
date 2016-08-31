/*
 * Copyright 2016 © 59store.com.
 *
 * Remote.java
 *
 */
package com.store59.kylin.rpc.context.annotation;

import java.lang.annotation.*;

/**
 * 远程服务client自动注入注解
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1.1 16/11/16
 * @since 2.1.1
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RemoteResource {

    /**
     * 超时时间, 单位为毫秒
     */
    long connectTimeout() default -1;

    /**
     * 超时时间, 单位为毫秒
     */
    long readTimeout() default -1;

    /**
     * 如果接口定义了@Remote注解, 会自动取@Remote定义的值, 该属性无需配置
     * 否则需要手工配置成, 保持和@RemoteService中的exportPath一致
     *
     * 远程服务暴露的path
     */
    String path() default "";

    /**
     * 保留属性
     * 用于本地调试
     * 如果配置了url, 则以url为主, 直接对url进行远程调用
     *
     * 例如: @RemoteResource(url = "http://192.168.30.41:7103/examplerpcservice", path="msg")
     *      private ExampleService exampleService;
     */
    String url() default "";

}
