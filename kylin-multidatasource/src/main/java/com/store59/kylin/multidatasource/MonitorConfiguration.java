/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.multidatasource;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Durid连接池监控配置
 *
 * @author <a href="mailto:zhongc@59store.com">士兵</a>
 * @version 1.0 16/8/9
 * @since 1.0
 */
@Configuration
public class MonitorConfiguration {

    @Bean
    public ServletRegistrationBean statViewServlet() {
        StatViewServlet servlet = new StatViewServlet();
        return new ServletRegistrationBean(servlet, "/druid/*");
    }
}
