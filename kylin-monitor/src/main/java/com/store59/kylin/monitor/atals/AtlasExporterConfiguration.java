/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.atals;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.netflix.servo.publish.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.export.Exporter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.netflix.metrics.atlas.AtlasMetricObserver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.netflix.servo.MonitorRegistry;

/**
 * Configures the Atlas metrics exporter.
 *
 * @author <a href="mailto:zhuzm@59store.com">天河</a>
 * @version 2.1 2016年8月18日
 * @since 2.1
 */
@Configuration
@ConditionalOnBean(AutoAtlasConfiguration.class)
public class AtlasExporterConfiguration {

    @Value("${kylin.monitor.atlas.metric.filter.prefixs:}")
    private String              filterPrefixs;
    @Autowired
    private MetricFilterProperties metricFilterProperties;

    private NavigableMap<String, MetricFilter> buildSubFilters(String filterPrefixs) {
        NavigableMap<String, MetricFilter> filters = new TreeMap();
        for (String filterPrefix : StringUtils.commaDelimitedListToStringArray(filterPrefixs)) {
            filters.put(filterPrefix, BasicMetricFilter.MATCH_NONE);
        }
        return filters;
    }

    @Bean
    public Exporter exporter(AtlasMetricObserver observer, MonitorRegistry monitorRegistry) {
        CompositeMetricFilter compositeMetricFilter = new CompositeMetricFilter();
        if (org.apache.commons.lang3.StringUtils.isNoneEmpty(filterPrefixs)) {
            // 兼容过去代码
            compositeMetricFilter.addFilter(new PrefixMetricFilter(null, BasicMetricFilter.MATCH_ALL, buildSubFilters(filterPrefixs)));
        }
        if (metricFilterProperties != null) {
            for (Map.Entry<String, String> m : metricFilterProperties.getFilters().entrySet()) {
                compositeMetricFilter.addFilter(
                        new PrefixMetricFilter(m.getKey(), BasicMetricFilter.MATCH_ALL, buildSubFilters(m.getValue())));
            }
        }

        // 配置混合poller, 目前jvm poller功能已经被SystemMeter等取代
//        Map<String, MetricPoller> metricPollerMap = new HashMap();
//        metricPollerMap.put("jvmMetricPoller", new JvmMetricPoller());
//        metricPollerMap.put("monitorRegistryMetricPoller", new MonitorRegistryMetricPoller(monitorRegistry));
//        CompositeMetricPoller compositeMetricPoller = new CompositeMetricPoller(metricPollerMap, Executors.newFixedThreadPool(30), 5000);
//        return new PrefixAtlasExporter(observer, compositeMetricPoller, new PrefixMetricFilter(null, BasicMetricFilter.MATCH_ALL, filters));
        return new PrefixAtlasExporter(observer, new MonitorRegistryMetricPoller(monitorRegistry), compositeMetricFilter);
    }

    static class PrefixAtlasExporter implements Exporter {
        private AtlasMetricObserver observer;
        private MetricPoller        poller;
        private MetricFilter        filter;

        public PrefixAtlasExporter(AtlasMetricObserver observer, MetricPoller poller, MetricFilter filter) {
            this.observer = observer;
            this.poller = poller;
            this.filter = filter;
        }

        @Override
        public void export() {
            observer.update(poller.poll(filter));
        }

    }

}
