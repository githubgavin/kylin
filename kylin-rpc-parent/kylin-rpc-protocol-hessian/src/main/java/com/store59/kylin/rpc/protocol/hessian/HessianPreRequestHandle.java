/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.protocol.hessian;

import org.apache.http.client.methods.HttpPost;

import java.util.ArrayList;

/**
 *
 * @author <a href="mailto:xudb@59store.com">锦萧</a>
 * @version 1.0 16/7/4
 * @since 1.0
 */
public class HessianPreRequestHandle {
    private static ArrayList<HessianPreRequestHook> preRequestHooks = new ArrayList<HessianPreRequestHook>();

    public static void preRequestHandle(HttpPost requestEntity){
        for (HessianPreRequestHook preQequestHook: preRequestHooks ) {
            preQequestHook.invoke(requestEntity);
        }
    }

    public static ArrayList<HessianPreRequestHook> getPreRequestHooks() {
        return preRequestHooks;
    }

    public static void addPreRequestHook(HessianPreRequestHook preRequestHook) {
        preRequestHooks.add(preRequestHook);
    }
}
