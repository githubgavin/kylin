/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.client.configuration;

import com.store59.kylin.rpc.client.ServiceUrlSelector;
import com.store59.kylin.rpc.client.utils.RPCClientNameManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 *
 * 该类用于解决rpc调用时因为初始化loadbanace导致的超时问题, 处理方法为, 程序启动时初始化所有client.
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/10/13
 * @since 2.1
 */
@Configuration
public class RpcClientAutoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(RpcClientAutoConfiguration.class);

    @Configuration
    static class RpcClientAutoInit implements SmartLifecycle {
        boolean isRunning = false;

        @Autowired
        ServiceUrlSelector serviceUrlSelector;

        @Override
        public boolean isAutoStartup() {
            return true;
        }

        @Override
        public void stop(Runnable callback) {
            isRunning = false;
        }

        @Override
        public void start() {
            logger.info("Starting rpc-clients init..");
            for (String name : RPCClientNameManager.getNames()) {
                try {
                    serviceUrlSelector.selectUrl(name);
                } catch (Exception e) {
                    logger.error("Init rpc client fail with {}", name);
                }
            }
            isRunning = true;
        }

        @Override
        public void stop() {
            isRunning = false;
        }

        @Override
        public boolean isRunning() {
            return isRunning;
        }

        @Override
        public int getPhase() {
            return Ordered.LOWEST_PRECEDENCE;
        }

    }

}
