/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor;

import com.netflix.spectator.api.Registry;
import com.netflix.spectator.api.Spectator;
import com.netflix.spectator.gc.GcLogger;
import com.netflix.spectator.jvm.Jmx;
import com.store59.kylin.monitor.spectator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.management.ManagementFactory;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 16/8/15
 * @since 1.0
 */
@Configuration
@ConditionalOnBean(MonitorInterceptorConfiguration.class)
public class AutoMonitorConfiguration {

    private GcLogger gc;

    @Autowired
    public void setRegistry(Registry registry) {
        Jmx.registerStandardMXBeans(registry);
        registerExtMXBeans(registry);
        Spectator.globalRegistry().add(registry);
        gc = new GcLogger();
        gc.start(null);
    }

    private void registerExtMXBeans(Registry registry) {
        registry.register(new SystemMeter(registry, ManagementFactory.getOperatingSystemMXBean()));
        registry.register(new ThreadMeter(registry, ManagementFactory.getThreadMXBean()));
        registry.register(new ClassLoadingMeter(registry, ManagementFactory.getClassLoadingMXBean()));
        registry.register(new GarbageCollectorMeter(registry, ManagementFactory.getGarbageCollectorMXBeans()));
    }

//    @Bean
//    @ConditionalOnMissingBean({CounterService.class, GaugeService.class})
//    public SpectatorMetricServices spectatorMetricServices(SpectatorGaugeCache spectatorGaugeCache) {
//        return spectatorGaugeCache;
//    }

    @Bean
    public SpectatorGaugeCache spectatorGaugeCache(Registry metricRegistry) {
        return new SpectatorGaugeCache(metricRegistry);
    }

}
