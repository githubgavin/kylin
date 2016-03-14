/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.context;

import org.springframework.core.env.ConfigurableEnvironment;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 16/3/8
 * @since 1.0
 */
public class SpringContext {

    private static ConfigurableEnvironment environment;

    public static ConfigurableEnvironment getEnvironment() {
        return environment;
    }

    public static void setEnvironment(ConfigurableEnvironment environment) {
        SpringContext.environment = environment;
    }
}
