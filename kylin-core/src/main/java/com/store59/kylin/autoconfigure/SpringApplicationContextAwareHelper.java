/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.autoconfigure;

import com.store59.kylin.context.SpringContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/5/13
 * @since 2.1
 */
public class SpringApplicationContextAwareHelper implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.setApplicationContext(applicationContext);
    }

}
