/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.rpc.utils.server.annotation;

import com.store59.kylin.rpc.context.annotation.Remote;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 16/8/24
 * @since 1.0
 */
@Remote(path = "/demo", connectTimeout = 1000, readTimeout = 2000)
public interface DemoApi {
}
