/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.atals;

import com.netflix.servo.monitor.MonitorConfig;
import com.netflix.servo.publish.MetricFilter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/9/14
 * @since 2.1
 */
public class CompositeMetricFilter implements MetricFilter {

    private List<MetricFilter> metricFilterList = new ArrayList<>();

    public CompositeMetricFilter() {
    }

    @Override
    public boolean matches(MonitorConfig config) {
        for (MetricFilter filter : metricFilterList) {
            if (!filter.matches(config)) {
                // 如果有被过滤的,则过滤掉
                return false;
            }
        }
        return true;
    }

    public boolean addFilter(MetricFilter filter) {
        return metricFilterList.add(filter);
    }


}
