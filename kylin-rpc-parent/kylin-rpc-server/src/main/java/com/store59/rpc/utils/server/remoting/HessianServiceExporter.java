/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.rpc.utils.server.remoting;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.remoting.caucho.HessianExporter;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.util.NestedServletException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author <a href="mailto:zhongc@59store.com">士兵</a>
 * @version 1.0 15/12/17
 * @since 1.0
 */
public class HessianServiceExporter extends HessianExporter implements HttpRequestHandler {

    public HessianServiceExporter() {
    }

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!"POST".equals(request.getMethod())) {
            throw new HttpRequestMethodNotSupportedException(request.getMethod(), new String[]{"POST"}, "HessianServiceExporter only supports POST requests");
        } else {
            response.setContentType("application/x-hessian");

            try {
                String requestId = request.getHeader("request-id");
                if(!StringUtils.isEmpty(requestId))
                    MDC.put("reqId", requestId);

                this.invoke(request.getInputStream(), response.getOutputStream());
            } catch (Throwable var4) {
                throw new NestedServletException("Hessian skeleton invocation failed", var4);
            }
        }
    }
}
