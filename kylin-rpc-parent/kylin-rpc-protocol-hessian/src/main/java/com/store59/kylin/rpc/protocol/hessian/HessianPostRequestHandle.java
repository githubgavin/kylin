/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.protocol.hessian;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;

import java.util.ArrayList;

/**
 *
 * @author <a href="mailto:xudb@59store.com">锦萧</a>
 * @version 1.0 16/7/4
 * @since 1.0
 */
public class HessianPostRequestHandle {
    private static ArrayList<HessianPostRequestHook> postRequestHooks = new ArrayList<HessianPostRequestHook>();

    public static void postRequestHandle(HttpPost requestEntity, HttpResponse responseEntity){
        for (HessianPostRequestHook postQequestHook: postRequestHooks ) {
            postQequestHook.invoke(requestEntity, responseEntity);
        }
    }

    public static void addPostRequestHook(HessianPostRequestHook postRequestHook) {
        postRequestHooks.add(postRequestHook);
    }
}
