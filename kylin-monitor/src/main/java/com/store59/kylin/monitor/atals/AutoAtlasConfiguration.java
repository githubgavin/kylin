/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.atals;

import com.store59.kylin.monitor.MonitorInterceptorConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.metrics.atlas.EnableAtlas;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 16/8/19
 * @since 1.0
 */
@EnableAtlas
@Configuration
@ConditionalOnBean(MonitorInterceptorConfiguration.class)
@ConditionalOnProperty(value = "kylin.monitor.atlas.enabled", havingValue = "true", matchIfMissing = true)
public class AutoAtlasConfiguration {
}
