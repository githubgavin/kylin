/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.log4j2;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import net.kencochrane.raven.Raven;
import net.kencochrane.raven.log4j2.SentryAppender;

/**
 * 扩展{@link SentryAppender}，支持不配置dsn属性.
 *
 * @author <a href="mailto:zhuzm@59store.com">天河</a>
 * @version 1.0 2016年3月9日
 * @since 1.0
 */
@Plugin(name = "ExtRaven", category = "Core", elementType = "appender", printObject = true)
public class ExtendedSentryAppender extends SentryAppender {
    private static final long serialVersionUID = -8179003120879506077L;

    /**
     * Create a Sentry Appender.
     *
     * @param name The name of the Appender.
     * @param enabled whether the Appender is enabled
     * @param dsn Data Source Name to access the Sentry server.
     * @param ravenFactory Name of the factory to use to build the {@link Raven} instance.
     * @param tags Tags to add to each event.
     * @param extraTags Tags to search through the MDC.
     * @param filter The filter, if any, to use.
     * @return The SentryAppender.
     */
    @PluginFactory
    public static SentryAppender createAppender(@PluginAttribute("name") final String name, @PluginAttribute("enabled") final boolean enabled,
                                                @PluginAttribute("dsn") final String dsn, @PluginAttribute("ravenFactory") final String ravenFactory,
                                                @PluginAttribute("tags") final String tags, @PluginAttribute("extraTags") final String extraTags,
                                                @PluginElement("filters") final Filter filter) {
        if (!enabled) {
            LOGGER.warn("SentryAppender is disabled");
            return null;
        }

        return SentryAppender.createAppender(name, dsn, ravenFactory, tags, extraTags, filter);
    }

}
