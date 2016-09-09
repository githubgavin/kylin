/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.spectator;

import com.netflix.spectator.api.AbstractMeter;
import com.netflix.spectator.api.Id;
import com.netflix.spectator.api.Measurement;
import com.netflix.spectator.api.Registry;
import com.store59.kylin.monitor.annotation.MonitorAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * 参考 {@link com.netflix.servo.publish.JvmMetricPoller}
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/9/6
 * @since 2.1
 */
public class SystemMeter extends AbstractMeter<OperatingSystemMXBean> {

    private static final Logger logger = LoggerFactory.getLogger(MonitorAspect.class);
    private final static String CLASS_NAME = OperatingSystemMXBean.class.getSimpleName();
    private final Id systemLoadAverage;
//    private final Id availableProcessors;
    private final Id maxFileDescriptorCount;
    private final Id openFileDescriptorCount;
    private final Id processCpuLoad;
    private final Id systemCpuLoad;
    private final Id committedVirtualMemorySize;
    private final Id totalPhysicalMemorySize;
    private final Id freePhysicalMemorySize;
    private final Id totalSwapSpaceSize;
    private final Id freeSwapSpaceSize;

    /**
     * Creates a new instance.
     *
     * @param registry
     *     Spectator registry to use for naming and clock source.
     * @param mbean
     *     Mbean to collect the data from.
     */
    public SystemMeter(Registry registry, OperatingSystemMXBean mbean) {
        super(registry.clock(), createId(registry, "system", mbean), mbean);
//        availableProcessors  = registry.createId("system.availableProcessors").withTag("id", mbean.getName());
        systemLoadAverage = createId(registry, "system.loadAverage", mbean);
        maxFileDescriptorCount  = createId(registry, "system.maxFileDescriptorCount", mbean);
        openFileDescriptorCount  = createId(registry, "system.openFileDescriptorCount", mbean);
        processCpuLoad  = createId(registry, "system.processCpuLoad", mbean);
        systemCpuLoad  = createId(registry, "system.systemCpuLoad", mbean);
        committedVirtualMemorySize  = createId(registry, "system.committedVirtualMemorySize", mbean);
        totalPhysicalMemorySize  = createId(registry, "system.totalPhysicalMemorySize", mbean);
        freePhysicalMemorySize  = createId(registry, "system.freePhysicalMemorySize", mbean);
        totalSwapSpaceSize  = createId(registry, "system.totalSwapSpaceSize", mbean);
        freeSwapSpaceSize  = createId(registry, "system.freeSwapSpaceSize", mbean);
    }

    private static Id createId(Registry registry, String name, OperatingSystemMXBean mbean) {
        return MeterHelper.createId(registry, name, mbean.getName(), CLASS_NAME);
    }

    @Override public Iterable<Measurement> measure() {
        final long timestamp = clock.wallTime();
        final OperatingSystemMXBean mbean = ref.get();
        final List<Measurement> ms = new ArrayList<>();
        if (mbean != null) {
//            ms.add(new Measurement(availableProcessors, timestamp, mbean.getAvailableProcessors()));
            ms.add(new Measurement(systemLoadAverage, timestamp, mbean.getSystemLoadAverage()));
            MeterHelper.addOptionalMeasurement(maxFileDescriptorCount, timestamp, mbean, "getMaxFileDescriptorCount", ms);
            MeterHelper.addOptionalMeasurement(openFileDescriptorCount, timestamp, mbean, "getOpenFileDescriptorCount", ms);
            MeterHelper.addOptionalMeasurement(processCpuLoad, timestamp, mbean, "getProcessCpuLoad", ms);
            MeterHelper.addOptionalMeasurement(systemCpuLoad, timestamp, mbean, "getSystemCpuLoad", ms);
            MeterHelper.addOptionalMeasurement(committedVirtualMemorySize, timestamp, mbean, "getCommittedVirtualMemorySize", ms);
            MeterHelper.addOptionalMeasurement(totalPhysicalMemorySize, timestamp, mbean, "getTotalPhysicalMemorySize", ms);
            MeterHelper.addOptionalMeasurement(freePhysicalMemorySize, timestamp, mbean, "getFreePhysicalMemorySize", ms);
            MeterHelper.addOptionalMeasurement(totalSwapSpaceSize, timestamp, mbean, "getTotalSwapSpaceSize", ms);
            MeterHelper.addOptionalMeasurement(freeSwapSpaceSize, timestamp, mbean, "getFreeSwapSpaceSize", ms);
        }
        return ms;
    }


}
