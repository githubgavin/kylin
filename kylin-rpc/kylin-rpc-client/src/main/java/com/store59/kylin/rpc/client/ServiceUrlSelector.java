/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.client;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.0.3 15/12/3
 * @since 2.0.3
 */
public interface ServiceUrlSelector {

    String selectUrl(String appName);

}
