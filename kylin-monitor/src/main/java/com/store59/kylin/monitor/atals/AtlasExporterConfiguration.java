/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.atals;

import java.util.NavigableMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.metrics.export.Exporter;
import org.springframework.cloud.netflix.metrics.atlas.AtlasMetricObserver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.google.common.collect.Maps;
import com.netflix.servo.MonitorRegistry;
import com.netflix.servo.publish.BasicMetricFilter;
import com.netflix.servo.publish.MetricFilter;
import com.netflix.servo.publish.MetricPoller;
import com.netflix.servo.publish.MonitorRegistryMetricPoller;
import com.netflix.servo.publish.PrefixMetricFilter;

/**
 * Configures the Atlas metrics exporter.
 * 
 * @author <a href="mailto:zhuzm@59store.com">天河</a>
 * @version 1.0 2016年8月18日
 * @since 1.0
 */
@Configuration
public class AtlasExporterConfiguration {

    @Value("${kylin.monitor.atlas.metric.filter.prefixs}")
    private String filterPrefixs;

    @Bean
    public Exporter exporter(AtlasMetricObserver observer, MonitorRegistry monitorRegistry) {
        NavigableMap<String, MetricFilter> filters = Maps.newTreeMap();
        for (String filterPrefix : StringUtils.commaDelimitedListToStringArray(filterPrefixs)) {
            filters.put(filterPrefix, BasicMetricFilter.MATCH_NONE);
        }

        return new PrefixAtlasExporter(observer, new MonitorRegistryMetricPoller(monitorRegistry),
                new PrefixMetricFilter(null, BasicMetricFilter.MATCH_ALL, filters));
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
