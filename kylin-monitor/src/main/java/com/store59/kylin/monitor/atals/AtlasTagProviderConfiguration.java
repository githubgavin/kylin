/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.atals;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.netflix.metrics.atlas.AtlasTagProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 16/8/15
 * @since 1.0
 */
@Configuration
@ConditionalOnBean(AutoAtlasConfiguration.class)
public class AtlasTagProviderConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(AtlasTagProviderConfiguration.class);

    @Bean
    AtlasTagProvider atlasCommonTags(@Value("${spring.application.name}") String appName) {
        return () -> {
            Map<String, String> map = new HashMap();
            map.put("app", appName);
            map.put("ip", getLocalHostAddress());
            return map;
        };
    }

    /**
     * 获取本地主机IP地址.
     *
     * @return 本地主机IP地址
     */
    private String getLocalHostAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
                        return inetAddress.getHostAddress().toString().trim();
                    }

                }
            }
        } catch (SocketException ex) {
            logger.error("获取本地主机IP地址失败", ex);
        }
        return "unknown";
    }

}
