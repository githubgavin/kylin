/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.atals;

import java.util.NavigableMap;
import java.util.TreeMap;
import com.netflix.servo.publish.*;
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

    @Value("${kylin.monitor.atlas.metric.filter.prefixs}")
    private String filterPrefixs;

    @Bean
    public Exporter exporter(AtlasMetricObserver observer, MonitorRegistry monitorRegistry) {
        NavigableMap<String, MetricFilter> filters = new TreeMap();
        for (String filterPrefix : StringUtils.commaDelimitedListToStringArray(filterPrefixs)) {
            filters.put(filterPrefix, BasicMetricFilter.MATCH_NONE);
        }
        // 配置混合poller, 目前jvm poller功能已经被SystemMeter等取代
//        Map<String, MetricPoller> metricPollerMap = new HashMap();
//        metricPollerMap.put("jvmMetricPoller", new JvmMetricPoller());
//        metricPollerMap.put("monitorRegistryMetricPoller", new MonitorRegistryMetricPoller(monitorRegistry));
//        CompositeMetricPoller compositeMetricPoller = new CompositeMetricPoller(metricPollerMap, Executors.newFixedThreadPool(30), 5000);
//        return new PrefixAtlasExporter(observer, compositeMetricPoller, new PrefixMetricFilter(null, BasicMetricFilter.MATCH_ALL, filters));
        return new PrefixAtlasExporter(observer, new MonitorRegistryMetricPoller(monitorRegistry), new PrefixMetricFilter(null, BasicMetricFilter.MATCH_ALL, filters));
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
