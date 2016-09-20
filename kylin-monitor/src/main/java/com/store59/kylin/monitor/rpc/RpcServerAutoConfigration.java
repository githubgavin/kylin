/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.rpc;

import com.store59.kylin.monitor.MonitorAutoConfiguration;
import com.store59.rpc.utils.server.interceptor.RemoteInvocationMonitorInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/9/19
 * @since 2.1
 */
@Configuration
@ConditionalOnBean(MonitorAutoConfiguration.class)
@ConditionalOnClass(RemoteInvocationMonitorInterceptor.class)
@ConditionalOnProperty(value = "kylin.monitor.rpc.server.enabled", havingValue = "true", matchIfMissing = true)
public class RpcServerAutoConfigration {

    @Bean
    @ConditionalOnProperty(value = "kylin.monitor.rpc.statsd.enabled", havingValue = "true", matchIfMissing = true)
    StatdRemoteInvocationMonitorInterceptor statdRemoteInvocationMonitorInterceptor() {
        return new StatdRemoteInvocationMonitorInterceptor();
    }

    @Bean
    DefaultRemoteInvocationMonitorInterceptor remoteInvocationMonitorInterceptor() {
        return new DefaultRemoteInvocationMonitorInterceptor();
    }

}
