/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.spectator;

import com.netflix.spectator.api.AbstractMeter;
import com.netflix.spectator.api.Id;
import com.netflix.spectator.api.Measurement;
import com.netflix.spectator.api.Registry;

import java.lang.management.ClassLoadingMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/9/7
 * @since 2.1
 */
public class ClassLoadingMeter extends AbstractMeter<ClassLoadingMXBean> {
    private final static String CLASS_NAME = ClassLoadingMXBean.class.getSimpleName();
    private final Id totalClassesCount;
    private final Id loadedClassesCount;
    private final Id unloadedClassesCount;

    /**
     * Creates a new instance.
     *
     * @param registry Spectator registry to use for naming and clock source.
     * @param mbean    Mbean to collect the data from.
     */
    public ClassLoadingMeter(Registry registry, ClassLoadingMXBean mbean) {
        super(registry.clock(), registry.createId("jvm.classes").withTag("class", CLASS_NAME), mbean);
        totalClassesCount = registry.createId("jvm.classes.total").withTag("class", CLASS_NAME);
        loadedClassesCount = registry.createId("jvm.classes.loaded").withTag("class", CLASS_NAME);
        unloadedClassesCount = registry.createId("jvm.classes.unloaded").withTag("class", CLASS_NAME);
    }

    @Override
    public Iterable<Measurement> measure() {
        final long timestamp = clock.wallTime();
        final ClassLoadingMXBean mbean = ref.get();
        final List<Measurement> ms = new ArrayList<>();
        if (mbean != null) {
            ms.add(new Measurement(totalClassesCount, timestamp, mbean.getTotalLoadedClassCount()));
            ms.add(new Measurement(loadedClassesCount, timestamp, mbean.getLoadedClassCount()));
            ms.add(new Measurement(unloadedClassesCount, timestamp, mbean.getUnloadedClassCount()));
        }
        return ms;
    }
}
