/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.annotation;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/9/7
 * @since 2.1
 */
public enum MonitorType {

    /**
     *  时间维度统计, 包括每秒最大值, 频率, 每秒总耗时等
     *
     * */
    TIMER,

    /**
     *  累计值统计, long
     *
     * */
    COUNTER,

    /**
     *  数值统计, double
     *
     * */
    Gauge

}
