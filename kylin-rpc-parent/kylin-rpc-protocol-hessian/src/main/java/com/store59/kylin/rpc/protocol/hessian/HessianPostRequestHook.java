/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.protocol.hessian;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;

/**
 *
 * @author <a href="mailto:xudb@59store.com">锦萧</a>
 * @version 1.0 16/7/4
 * @since 1.0
 */
public abstract class HessianPostRequestHook {
    public abstract void invoke(HttpPost requestEntity, HttpResponse responseEntity);
}
