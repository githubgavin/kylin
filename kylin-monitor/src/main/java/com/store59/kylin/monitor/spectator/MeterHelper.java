/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.spectator;

import com.netflix.spectator.api.Id;
import com.netflix.spectator.api.Measurement;
import com.netflix.spectator.api.Registry;
import com.store59.kylin.monitor.annotation.MonitorAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/9/7
 * @since 2.1
 */
public class MeterHelper {

    private static final Logger logger = LoggerFactory.getLogger(MeterHelper.class);

    static void addOptionalMeasurement(
            Id id,
            long timestamp,
            Object obj,
            String methodName,
            List<Measurement> ms) {
        try {
            Method method = obj.getClass().getMethod(methodName);
            method.setAccessible(true);
            double value = (double) method.invoke(obj);
            ms.add(new Measurement(id, timestamp, value));
        } catch (Exception e) {
            final String msg = String.format("failed to get value for %s.%s",
                    obj.getClass().getName(), methodName);
            logger.debug(msg, e);
        }
    }

    static Id createId(Registry registry, String name, String tagId, String tagClassName) {
        return registry.createId(name).withTag("id", tagId).withTag("class", tagClassName);
    }

    static Id createId(Registry registry, String name, String tagClassName) {
        return registry.createId(name).withTag("class", tagClassName);
    }
}
