/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.annotation;

import com.netflix.servo.monitor.MonitorConfig;
import com.store59.kylin.monitor.atals.AtlasAutoConfiguration;
import com.store59.kylin.monitor.spectator.SpectatorGaugeCache;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.netflix.metrics.servo.ServoMonitorCache;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/9/7
 * @since 2.1
 */
@Component
@Aspect
@ConditionalOnBean(AtlasAutoConfiguration.class)
public class MonitorAspect {

    private static final Logger logger = LoggerFactory.getLogger(MonitorAspect.class);
    @Autowired
    ServoMonitorCache   servoMonitorCache;
    @Autowired
    SpectatorGaugeCache spectatorGaugeCache;

    @Pointcut("@annotation(com.store59.kylin.monitor.annotation.Monitor)")
    public void monitorMethodAspect() {
    }

    @Around("monitorMethodAspect()")
    public Object doMonitor(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        Object ret = joinPoint.proceed();
        try {
            Monitor monitor = getMonitor(joinPoint);
            if (monitor != null) {
                String metricName = monitor.metricName();
                MonitorType monitorType = monitor.type();
                if (StringUtils.isEmpty(metricName)) {
                    String className = joinPoint.getTarget().getClass().getSimpleName();
                    String methodName = joinPoint.getSignature().getName();
                    metricName = className + "." + methodName;
                }
                Map<String, String> tagsMap = new HashMap<>();
                tagsMap.put("annotation", "monitor");
                for (Tag tag : monitor.tags()) {
                    tagsMap.put(tag.key(), tag.value());
                }
                if (monitor.types().length > 0) {
                    for (MonitorType monitorType1 : monitor.types()) {
                        record(monitorType1, metricName, tagsMap, startTime, ret, joinPoint);
                    }
                } else {
                    record(monitorType, metricName, tagsMap, startTime, ret, joinPoint);
                }

            }
        } catch (Exception e) {
            logger.error("@Monitor fail with " + e.getMessage(), e);
        }


        return ret;
    }

    private void record(MonitorType monitorType, String metricName, Map<String, String> tagsMap,
                        long startTime, Object ret, JoinPoint joinPoint) {
        switch (monitorType) {
            case TIMER:
                MonitorConfig.Builder monitorConfigBuilder = MonitorConfig.builder(metricName);
                for (Map.Entry<String, String> entry : tagsMap.entrySet()) {
                    monitorConfigBuilder.withTag(entry.getKey(), entry.getValue());
                }
                servoMonitorCache.getTimer(monitorConfigBuilder.build()).record(System.nanoTime() - startTime, TimeUnit.NANOSECONDS);
                break;
            case COUNTER:
                spectatorGaugeCache.increment(metricName, tagsMap);
                break;
            case Gauge:
                if (ret instanceof Number) {
                    spectatorGaugeCache.submit(metricName, tagsMap, (double)ret);
                } else {
                    logger.warn("The return type of {}.{} is not a number, MonitorType should't be Gauge!",
                            joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());
                }
                break;
            default:
                break;
        }
    }

    static Monitor getMonitor(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    return method.getAnnotation(Monitor.class);
                }
            }
        }
        return null;
    }
}
