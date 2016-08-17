/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor;

import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/8/12
 * @since 2.1
 */
public class LogRequestIdHandlerInterceptor implements HandlerInterceptor {

    /**
     * 如果Request中不存在logRequestId, 则直接生成UUID
     * 注, request中的logRequestId由zuul生成
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String logRequestId = (String) RequestContextHolder.getRequestAttributes().getAttribute("logRequestId", SCOPE_REQUEST);
        if (logRequestId == null) {
            logRequestId = UUID.randomUUID().toString();
        }
        MDC.put("reqId", logRequestId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
