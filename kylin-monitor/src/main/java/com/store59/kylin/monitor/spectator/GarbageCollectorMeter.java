/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.spectator;

import com.netflix.spectator.api.AbstractMeter;
import com.netflix.spectator.api.Id;
import com.netflix.spectator.api.Measurement;
import com.netflix.spectator.api.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/9/7
 * @since 2.1
 */
public class GarbageCollectorMeter extends AbstractMeter<List<GarbageCollectorMXBean>> {

    private static       Logger logger     = LoggerFactory.getLogger(GarbageCollectorMeter.class);
    private final static String CLASS_NAME = GarbageCollectorMXBean.class.getSimpleName();
    private final Id collectionCount;
    private final Id collectionTime;

    /**
     * Creates a new instance.
     *
     * @param registry Spectator registry to use for naming and clock source.
     */
    public GarbageCollectorMeter(Registry registry) {
        super(registry.clock(), registry.createId("jvm.collection").withTag("class", CLASS_NAME), null);
        collectionCount = registry.createId("jvm.collection.count").withTag("class", CLASS_NAME);
        collectionTime = registry.createId("jvm.collection.time").withTag("class", CLASS_NAME);
    }

    @Override
    public Iterable<Measurement> measure() {
        final long timestamp = clock.wallTime();
        final List<GarbageCollectorMXBean> mbeans = ManagementFactory.getGarbageCollectorMXBeans();
        final List<Measurement> ms = new ArrayList<>();
        if (mbeans != null) {
            for (GarbageCollectorMXBean mbean : mbeans) {
                ms.add(new Measurement(collectionCount.withTag("id", mbean.getName()), timestamp, mbean.getCollectionCount()));
                ms.add(new Measurement(collectionTime.withTag("id", mbean.getName()), timestamp, mbean.getCollectionTime()));
            }
        }
        return ms;
    }

    @Override
    public boolean hasExpired() {
        return false;
    }
}
