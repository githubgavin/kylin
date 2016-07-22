/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.client;

import com.store59.kylin.rpc.client.exceptions.NoServiceUrlFoundException;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/06/14
 * @since 2.0.3
 */
public interface ServiceUrlSelector {

    String selectUrl(String appName) throws NoServiceUrlFoundException ;

}
