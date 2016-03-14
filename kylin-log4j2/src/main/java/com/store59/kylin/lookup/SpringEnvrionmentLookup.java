/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.lookup;

import com.store59.kylin.context.SpringContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.AbstractLookup;
import org.apache.logging.log4j.core.lookup.StrLookup;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 16/3/8
 * @since 1.0
 */
@Plugin(name = "spring", category = StrLookup.CATEGORY)
public class SpringEnvrionmentLookup extends AbstractLookup {

    /**
     * Looks up the value of the spring environment variable.
     *
     * @param event The current LogEvent (is ignored by this StrLookup).
     * @param key   the key to be looked up, may be null
     * @return The value of the spring environment variable.
     */
    @Override
    public String lookup(final LogEvent event, final String key) {
        if (SpringContext.getEnvironment() != null) {
            return SpringContext.getEnvironment().getProperty(key);
        }
        return null;
    }

}
