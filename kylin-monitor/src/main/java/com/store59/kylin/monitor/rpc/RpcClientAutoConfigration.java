/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.rpc;

import com.netflix.servo.monitor.MonitorConfig;
import com.store59.kylin.monitor.MonitorAutoConfiguration;
import com.store59.kylin.monitor.spectator.SpectatorGaugeCache;
import com.store59.kylin.rpc.protocol.hessian.HessianPostRequestHandle;
import com.store59.kylin.rpc.protocol.hessian.HessianPostRequestHook;
import com.store59.kylin.rpc.protocol.hessian.HessianPreRequestHandle;
import com.store59.kylin.rpc.protocol.hessian.HessianPreRequestHook;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.metrics.servo.ServoMonitorCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/9/19
 * @since 2.1
 */
@Configuration
@ConditionalOnBean(MonitorAutoConfiguration.class)
@ConditionalOnClass({HessianPostRequestHandle.class, HessianPreRequestHandle.class})
@ConditionalOnProperty(value = "kylin.monitor.rpc.client.enabled", havingValue = "true", matchIfMissing = true)
public class RpcClientAutoConfigration {

    private final static Logger logger = LoggerFactory.getLogger(DefaultRemoteInvocationMonitorInterceptor.class);
    @Value("${kylin.monitor.rpc.metricName:rpc}")
    String metricName;
    @Autowired
    private ServoMonitorCache   servoMonitorCache;
    @Autowired
    private SpectatorGaugeCache spectatorGaugeCache;
    private ThreadLocal<SoftReference<RpcInfo>> rpcInfoThreadLocal = new ThreadLocal<>();

    @PostConstruct
    void registerMonitor() {
        HessianPreRequestHandle.addPreRequestHook(new HessianPreRequestMonitorHook(rpcInfoThreadLocal));
        HessianPostRequestHandle.addPostRequestHook(new HessianPostRequestMonitorHook(rpcInfoThreadLocal, servoMonitorCache, spectatorGaugeCache));
    }

    class HessianPreRequestMonitorHook extends HessianPreRequestHook {

        ThreadLocal<SoftReference<RpcInfo>> rpcInfoThreadLocal;

        public HessianPreRequestMonitorHook(ThreadLocal<SoftReference<RpcInfo>> rpcInfoThreadLocal) {
            this.rpcInfoThreadLocal = rpcInfoThreadLocal;
        }

        @Override
        public void invoke(HttpPost requestEntity) {
            try {
                URI uri = requestEntity.getURI();
                rpcInfoThreadLocal.set(new SoftReference(new RpcInfo(System.nanoTime(), uri.getHost(), uri.getPath())));
            } catch (Exception e) {
                logger.error("HessianPreRequestMonitorHook invoke fail");
            }
        }

    }

    class HessianPostRequestMonitorHook extends HessianPostRequestHook {

        ThreadLocal<SoftReference<RpcInfo>> rpcInfoThreadLocal;
        ServoMonitorCache                   servoMonitorCache;
        SpectatorGaugeCache                 spectatorGaugeCache;

        public HessianPostRequestMonitorHook(ThreadLocal<SoftReference<RpcInfo>> rpcInfoThreadLocal, ServoMonitorCache servoMonitorCache, SpectatorGaugeCache spectatorGaugeCache) {
            this.rpcInfoThreadLocal = rpcInfoThreadLocal;
            this.servoMonitorCache = servoMonitorCache;
            this.spectatorGaugeCache = spectatorGaugeCache;
        }

        /**
         * 和SpringCloud中DefaultMetricsTagProvider类同名方法保持一致
         *
         */
        private String sanitizeUrlTemplate(String urlTemplate) {
            String sanitized = urlTemplate.replaceAll("/", "_").replaceAll("[{}]", "-");
            if (!StringUtils.hasText(sanitized)) {
                sanitized = "none";
            }
            return sanitized;
        }

        @Override
        public void invoke(HttpPost requestEntity, HttpResponse responseEntity) {
            try {
                SoftReference<RpcInfo> soft = rpcInfoThreadLocal.get();
                if (soft != null) {
                    RpcInfo rpcInfo = soft.get();
                    Map<String, String> tagsMap = new HashMap();
                    tagsMap.put("rtype", "client");
                    tagsMap.put("uri", sanitizeUrlTemplate(rpcInfo.uri.substring(1)));
                    tagsMap.put("host", rpcInfo.host);
                    MonitorConfig.Builder monitorConfigBuilder = MonitorConfig.builder(metricName);
                    for (Map.Entry<String, String> entry : tagsMap.entrySet()) {
                        monitorConfigBuilder.withTag(entry.getKey(), entry.getValue());
                    }
                    spectatorGaugeCache.increment(metricName, tagsMap);
                    servoMonitorCache.getTimer(monitorConfigBuilder.build()).record(System.nanoTime() - rpcInfo.startTime, TimeUnit.NANOSECONDS);
                }
            } catch (Exception e) {
                logger.error("HessianPostRequestMonitorHook invoke fail");
            } finally {
                rpcInfoThreadLocal.remove();
            }
        }

    }

    class RpcInfo {
        long   startTime;
        String host;
        String uri;

        public RpcInfo(long startTime, String host, String uri) {
            this.startTime = startTime;
            this.host = host;
            this.uri = StringUtils.isEmpty(uri) ? "/" : uri;
        }
    }
}
