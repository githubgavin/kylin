/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor;

import com.netflix.spectator.api.Registry;
import com.netflix.spectator.api.Spectator;
import com.netflix.spectator.gc.GcLogger;
import com.netflix.spectator.jvm.Jmx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 16/8/15
 * @since 1.0
 */
@Configuration
@ConditionalOnBean(MonitorInterceptorConfiguration.class)
public class JvmMonitorConfiguration {

    private GcLogger gc;

    @Autowired
    public void setRegistry(Registry registry) {
        Jmx.registerStandardMXBeans(registry);
        Spectator.globalRegistry().add(registry);
        gc = new GcLogger();
        gc.start(null);
    }

}
