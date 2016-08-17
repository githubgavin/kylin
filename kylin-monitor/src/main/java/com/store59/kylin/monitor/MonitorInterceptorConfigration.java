/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.netflix.metrics.atlas.EnableAtlas;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 16/8/12
 * @since 1.0
 */
@Configuration
@ConditionalOnProperty(value = "kylin.monitor.enabled", havingValue = "true", matchIfMissing = true)
public class MonitorInterceptorConfigration {

    @Configuration
    @ConditionalOnWebApplication
    static class MonitorWebResourceConfiguration extends WebMvcConfigurerAdapter {
        @Bean
        LogRequestIdHandlerInterceptor logRequestIdHandlerInterceptor() {
            return new LogRequestIdHandlerInterceptor();
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(logRequestIdHandlerInterceptor());
        }
    }

    @EnableAtlas
    @Configuration
    @ConditionalOnProperty(value = "kylin.monitor.atlas.enabled", havingValue = "true", matchIfMissing = true)
    static class AtlasPushConfigration {
    }
}
