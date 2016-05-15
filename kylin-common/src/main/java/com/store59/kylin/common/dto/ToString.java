/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.common.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 规范对象<code>toString</code>的输出.
 *
 * @author <a href="mailto:zhuzm@59store.com">天河</a>
 * @version 1.0 2016年3月17日
 * @since 1.0
 */
public abstract class ToString {

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
