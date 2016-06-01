/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.monitor.statd;

import java.io.IOException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Statsd客户端配置.
 * 
 * @author <a href="mailto:zhuzm@59store.com">天河</a>
 * @version 2.0 2015年10月26日
 * @since 2.0
 */
@Configuration
@EnableConfigurationProperties(StatsdProperties.class)
//@PropertySource("classpath:statsd.properties")
public class StatsdConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(StatsdConfiguration.class);

    @Bean
    public StatsdClient statsdClient(StatsdProperties statsdProperties) throws UnknownHostException, IOException {
        if (!statsdProperties.isEnabled()) {
            logger.warn("Statsd is disabled");
            return null;
        }

        StatsdClient client = new StatsdClient(statsdProperties.getHost(), statsdProperties.getPort());

        if (statsdProperties.getBufferSize() > 0) {
            client.setBufferSize(statsdProperties.getBufferSize());
        }

        if (statsdProperties.getFlushPeriod() > 0) {
            client.startFlushTimer(statsdProperties.getFlushPeriod());
        }

        client.enableMultiMetrics(statsdProperties.isMultiMetrics());

        return client;
    }

}
