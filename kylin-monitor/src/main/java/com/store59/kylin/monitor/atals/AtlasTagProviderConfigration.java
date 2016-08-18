/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.atals;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.metrics.atlas.AtlasTagProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableMap;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 16/8/15
 * @since 1.0
 */
@Configuration
public class AtlasTagProviderConfigration {
    private static final Logger logger = LoggerFactory.getLogger(AtlasTagProviderConfigration.class);

    @Bean
    AtlasTagProvider atlasCommonTags(@Value("${spring.application.name}") String appName) {
        return () -> ImmutableMap.of("app", appName, "ip", getLocalHostAddress());
    }

    public static void main(String[] args) {
        System.out.println(ImmutableMap.of("app", null));
    }

    /**
     * 获取本地主机IP地址.
     * 
     * @return 本地主机IP地址
     */
    private String getLocalHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("获取本地主机IP地址失败", e);
            return "none";
        }
    }

}
