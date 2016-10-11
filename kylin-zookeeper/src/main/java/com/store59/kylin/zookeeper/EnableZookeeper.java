/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.zookeeper;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/9/23
 * @since 2.1
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(ZookeeperAutoConfiguration.class)
public @interface EnableZookeeper {
}
