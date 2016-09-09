/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.spectator;

import com.netflix.spectator.api.Id;
import com.netflix.spectator.api.Registry;
import com.netflix.spectator.impl.AtomicDouble;
import org.springframework.cloud.netflix.metrics.spectator.SpectatorMetricServices;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/9/7
 * @since 2.1
 */
public class SpectatorGaugeCache extends SpectatorMetricServices {

    private final Registry registry;
    private final ConcurrentMap<Id, AtomicLong>   counters = new ConcurrentHashMap();
    private final ConcurrentMap<Id, AtomicDouble> gauges   = new ConcurrentHashMap();

    public SpectatorGaugeCache(Registry registry) {
        super(registry);
        this.registry = registry;
    }

    public void increment(String name, Map<String, String> tags) {
        this.incrementInternal(name, tags, 1L);
    }

    public void decrement(String name, Map<String, String> tags) {
        this.incrementInternal(name, tags, -1L);
    }

    private void incrementInternal(String name, Map<String, String> tags, long value) {
        if(!name.startsWith("status.")) {
            if(name.startsWith("meter.")) {
                this.registry.counter(stripMetricName(name)).increment(value);
            } else {
                Id id = this.registry.createId(name).withTags(tags);
                AtomicLong gauge = this.getCounterStorage(id);
                gauge.addAndGet(value);
            }
        }
    }

    public void submit(String name, Map<String, String> tags, double dValue) {
        if(name.startsWith("histogram.")) {
            this.registry.distributionSummary(stripMetricName(name)).record((long)dValue);
        } else if(name.startsWith("timer.")) {
            long id = (long)(dValue * 1000000.0D);
            this.registry.timer(stripMetricName(name)).record(id, TimeUnit.NANOSECONDS);
        } else {
            Id id1 = this.registry.createId(name).withTags(tags);
            AtomicDouble gauge = this.getGaugeStorage(id1);
            gauge.set(dValue);
        }
    }

    private AtomicDouble getGaugeStorage(Id id) {
        AtomicDouble newGauge = new AtomicDouble(0.0D);
        AtomicDouble existingGauge = (AtomicDouble) this.gauges.putIfAbsent(id, newGauge);
        return existingGauge == null ? (AtomicDouble) this.registry.gauge(id, newGauge) : existingGauge;
    }

    private AtomicLong getCounterStorage(Id id) {
        AtomicLong newCounter = new AtomicLong(0L);
        AtomicLong existingCounter = (AtomicLong) this.counters.putIfAbsent(id, newCounter);
        return existingCounter == null ? (AtomicLong) this.registry.gauge(id, newCounter) : existingCounter;
    }
}
