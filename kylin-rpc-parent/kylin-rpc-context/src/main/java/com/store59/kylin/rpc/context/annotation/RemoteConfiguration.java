/*
 * Copyright 2016 © 59store.com.
 *
 * Remote.java
 *
 */
package com.store59.kylin.rpc.context.annotation;

import org.springframework.context.annotation.Configuration;
import java.lang.annotation.*;

/**
 * 远程服务接口定义注解, 保留注解, 便于功能待扩展
 *
 * 例如:
 *
 * @RemoteConfiguration
 * public class RemoteClientsConfiguration {
 *
 *     @RemoteBean
 *     OrderService orderService;
 *
 *     @RemoteBean(connectTimeout = 10000, readTimeout = 5000)
 *     UserService userService;
 *
 *     @RemoteBean(connectTimeout = 2000, readTimeout = 1000)
 *     UserService userServiceShorter;
 * }
 *
 * @Service
 * public class XXXServiceImpl {
 *
 *     @RemoteResource
 *     private OrderService orderService;
 *
 *     @RemoteResource
 *     private userService userService;
 *
 *     @RemoteResource
 *     private userService userServiceShorter;
 *
 *     public boolean XXXX() {
 *         ...
 *         orderService.createOrder(...);
 *         ...
 *         User user = userService.findUserById(20);
 *         ...
 *         userServiceShorter.add(..);
 *         ...
 *     }
 *
 * }
 *
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1.1 16/11/16
 * @since 2.1.1
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@Deprecated
public @interface RemoteConfiguration {
}
