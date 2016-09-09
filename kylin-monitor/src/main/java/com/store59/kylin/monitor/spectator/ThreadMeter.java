/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.spectator;

import com.netflix.spectator.api.AbstractMeter;
import com.netflix.spectator.api.Id;
import com.netflix.spectator.api.Measurement;
import com.netflix.spectator.api.Registry;

import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/9/7
 * @since 2.1
 */
public class ThreadMeter extends AbstractMeter<ThreadMXBean> {

    private final static String CLASS_NAME = ThreadMXBean.class.getSimpleName();
    private final Id peekThreadCount;
    private final Id daemonThreadCount;
    private final Id totalStartedThreadCount;
    private final Id threadCount;

    /**
     * Creates a new instance.
     *
     * @param registry Spectator registry to use for naming and clock source.
     * @param mbean    Mbean to collect the data from.
     */
    public ThreadMeter(Registry registry, ThreadMXBean mbean) {
        super(registry.clock(), registry.createId("jvm.threads").withTag("class", CLASS_NAME), mbean);
        peekThreadCount = registry.createId("jvm.threads.peek").withTag("class", CLASS_NAME);
        daemonThreadCount = registry.createId("jvm.threads.daemon").withTag("class", CLASS_NAME);
        totalStartedThreadCount = registry.createId("jvm.threads.totalStarted").withTag("class", CLASS_NAME);
        threadCount = registry.createId("jvm.threads.count").withTag("class", CLASS_NAME);
    }

    @Override
    public Iterable<Measurement> measure() {
        final long timestamp = clock.wallTime();
        final ThreadMXBean mbean = ref.get();
        final List<Measurement> ms = new ArrayList<>();
        if (mbean != null) {
            ms.add(new Measurement(peekThreadCount, timestamp, mbean.getPeakThreadCount()));
            ms.add(new Measurement(daemonThreadCount, timestamp, mbean.getDaemonThreadCount()));
            ms.add(new Measurement(totalStartedThreadCount, timestamp, mbean.getTotalStartedThreadCount()));
            ms.add(new Measurement(threadCount, timestamp, mbean.getThreadCount()));
        }
        return ms;
    }
}
