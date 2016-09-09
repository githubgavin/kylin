/*
 * Copyright 2016 © 59store.com.
 *
 * Remote.java
 *
 */
package com.store59.kylin.rpc.context.annotation;

import java.lang.annotation.*;

/**
 * 远程服务接口注解, 用于定义rpc默认配置, 以及申明rpc
 *
 * 使用方法例如:
 *
 * @Remote
 * public interface MsgService {
 *   List<Message> getAllMsgList();
 *   ...
 * }
 *
 * 也可以设置接口建议默认超时时间, 该设置用于客户端不指定情况下默认使用;
 * path配置服务暴露路径, 缺省为接口全路径名(例如: /com.store59.examplerpc.MsgService)
 *
 * @Remote(path="/msg", connectTimeout=2000, readTimeout=1500)
 * public interface MsgService {
 *   List<Message> getAllMsgList();
 *   ...
 * }
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1.1 16/11/16
 * @since 2.1.1
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Remote {

    /**
     * 暴露路径,保留字段,建议不填,默认为包全路径
     */
    String path() default "";

    /**
     * 服务端建议客户端默认超时时间, 单位为毫秒
     */
    long connectTimeout() default -1;

    /**
     * 服务端建议客户端默认超时时间, 单位为毫秒
     */
    long readTimeout() default -1;

}
