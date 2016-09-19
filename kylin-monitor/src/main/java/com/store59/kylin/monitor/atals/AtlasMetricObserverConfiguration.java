/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.monitor.atals;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.netflix.metrics.atlas.AtlasMetricObserver;
import org.springframework.context.annotation.Configuration;

/**
 * Configures the Atlas metrics observer.
 * 
 * @author <a href="mailto:zhuzm@59store.com">天河</a>
 * @version 1.0 2016年8月17日
 * @since 1.0
 */
@Configuration
@ConditionalOnBean(AtlasAutoConfiguration.class)
public class AtlasMetricObserverConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(AtlasMetricObserverConfiguration.class);

    /**
     * 修改AtlasMetricObserver类私有静态属性validAtlasTag.
     * 
     * @param observer {@link AtlasMetricObserver}
     */
    @Autowired(required = false)
    public void updateAtlasMetricObserver(AtlasMetricObserver observer) {
        try {
            Field field = AtlasMetricObserver.class.getDeclaredField("validAtlasTag");
            field.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(null, Pattern.compile(".*"));
        } catch (Exception e) {
            logger.error("修改AtlasMetricObserver类私有静态属性validAtlasTag异常", e);
        }
    }

}
