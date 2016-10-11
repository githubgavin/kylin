/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.integration.zookeeper.config.CuratorFrameworkFactoryBean;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;
import org.springframework.integration.zookeeper.metadata.ZookeeperMetadataStore;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/9/23
 * @since 2.1
 */
@Configuration
public class ZookeeperAutoConfiguration {

    @Bean
    CuratorFrameworkFactoryBean zkClient(@Value("${zookeeper.connectString}") String connectString) {
        return new CuratorFrameworkFactoryBean(connectString);
    }

    @Bean
    MetadataStore zkMetadataStore(CuratorFramework zkClient) throws Exception {
        return new ZookeeperMetadataStore(zkClient);
    }

    @Bean
    LockRegistry zkLockRegistry(CuratorFramework zkClient) {
        return new ZookeeperLockRegistry(zkClient);
    }

}
