/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.spectator;

import com.netflix.spectator.api.AbstractMeter;
import com.netflix.spectator.api.Id;
import com.netflix.spectator.api.Measurement;
import com.netflix.spectator.api.Registry;

import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/9/7
 * @since 2.1
 */
public class GarbageCollectorMeter extends AbstractMeter<List<GarbageCollectorMXBean>> {

    private final static String CLASS_NAME = GarbageCollectorMXBean.class.getSimpleName();
    private final Id collectionCount;
    private final Id collectionTime;

    /**
     * Creates a new instance.
     *
     * @param registry Spectator registry to use for naming and clock source.
     * @param mbeans   Mbean to collect the data from.
     */
    public GarbageCollectorMeter(Registry registry, List<GarbageCollectorMXBean> mbeans) {
        super(registry.clock(), registry.createId("jvm.gc").withTag("class", CLASS_NAME), mbeans);
        collectionCount = registry.createId("jvm.gc.collectionCount").withTag("class", CLASS_NAME);
        collectionTime = registry.createId("jvm.gc.collectionTime").withTag("class", CLASS_NAME);
    }

    @Override
    public Iterable<Measurement> measure() {
        final long timestamp = clock.wallTime();
        final List<GarbageCollectorMXBean> mbeans = ref.get();
        final List<Measurement> ms = new ArrayList<>();
        if (mbeans != null) {
            for (GarbageCollectorMXBean mbean : mbeans) {
                ms.add(new Measurement(collectionCount.withTag("id", mbean.getName()), timestamp, mbean.getCollectionCount()));
                ms.add(new Measurement(collectionTime.withTag("id", mbean.getName()), timestamp, mbean.getCollectionTime()));
            }
        }
        return ms;
    }
}
