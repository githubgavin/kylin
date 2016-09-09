/*
 * Copyright 2016 © 59store.com.
 *
 * Remote.java
 *
 */
package com.store59.kylin.monitor.annotation;

import java.lang.annotation.*;

/**
 * 监控指标注解, 用于增加自定义监控指标
 *
 * 使用方法例如:
 *
 * public class OrderService {
 *
 *   @Monitor(metricName="OrderService.createOrder")
 *   boolean createOrder(Order order);
 *   ...
 * }
 *
 * 如果不指定metricName, 默认为类名加方法名
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1.1 16/11/16
 * @since 2.1.1
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Monitor {

    /**
     * 跟踪指标命名, 默认为类名加方法名
     */
    String metricName() default "";

    /**
     * 统计类型, 默认为timer统计, types不为空时, 该属性失效
     */
    MonitorType type() default MonitorType.TIMER;

    /**
     * 多个统计类型同时使用, 不为空时, type属性失效
     */
    MonitorType[] types() default {};

    /**
     * 该指标其他维度的描述, 可用于分类
     *
     */
    Tag[] tags() default {};

}
