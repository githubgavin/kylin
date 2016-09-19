/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.rpc;

import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.tag.SmallTagMap;
import com.netflix.servo.tag.Tags;
import com.store59.kylin.monitor.spectator.SpectatorGaugeCache;
import com.store59.kylin.monitor.statsd.StatsdClient;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.metrics.MetricsTagProvider;
import org.springframework.cloud.netflix.metrics.servo.ServoMonitorCache;
import org.springframework.util.ClassUtils;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/9/19
 * @since 2.1
 */
public class DefaultRemoteInvocationMonitorInterceptor implements MethodInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(DefaultRemoteInvocationMonitorInterceptor.class);
    @Value("${kylin.monitor.rpc.metricName:rpc}")
    String metricName;
    @Autowired
    private ServoMonitorCache   servoMonitorCache;
    @Autowired
    private SpectatorGaugeCache spectatorGaugeCache;
    @Autowired(required = false)
    private StatdRemoteInvocationMonitorInterceptor statdRemoteInvocationMonitorInterceptor;
    /**
     * @see MethodInterceptor#invoke(MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long startTime = System.nanoTime();
        boolean exceptionOcr = false;
        Map<String, String> tagsMap = new HashMap();
        try {
            Method method = invocation.getMethod();
            String className = method.getDeclaringClass().getName();
            String methodName = method.getName();
            tagsMap.put("rtype", "server");
            tagsMap.put("method", methodName);
            tagsMap.put("class", className);
        } catch (Exception e) {
            logger.error("RPC monitor Exception: ", e);
        }
        try {
            if (statdRemoteInvocationMonitorInterceptor == null) {
                return invocation.proceed();
            }
            return statdRemoteInvocationMonitorInterceptor.invoke(invocation);
        } catch (Exception e) {
            exceptionOcr = true;
            throw e;
        } finally {
            tagsMap.put("hasException", String.valueOf(exceptionOcr));
            MonitorConfig.Builder monitorConfigBuilder = MonitorConfig.builder(metricName);
            for (Map.Entry<String, String> entry : tagsMap.entrySet()) {
                monitorConfigBuilder.withTag(entry.getKey(), entry.getValue());
            }
            spectatorGaugeCache.increment(metricName, tagsMap);
            servoMonitorCache.getTimer(monitorConfigBuilder.build()).record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
        }
    }

}
