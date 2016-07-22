/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.monitor.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.StopWatch;

import com.store59.kylin.rpc.monitor.statd.StatsdClient;

/**
 * 远程调用监控拦截器.
 * 
 * @author <a href="mailto:zhuzm@59store.com">天河</a>
 * @version 2.0 2015年10月26日
 * @since 2.0
 */
public class StatdRemoteInvocationMonitorInterceptor implements MethodInterceptor {

    @Autowired
    private StatsdClient statsdClient;

    /**
     * @see MethodInterceptor#invoke(MethodInvocation)
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (statsdClient == null) {
            return invocation.proceed();
        }

        Method method = invocation.getMethod();
        String methodName = ClassUtils.getQualifiedMethodName(method);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            statsdClient.increment(methodName);
            return invocation.proceed();
        } finally {
            stopWatch.stop();
            statsdClient.timing(methodName, (int) stopWatch.getTotalTimeMillis());
        }
    }

}
