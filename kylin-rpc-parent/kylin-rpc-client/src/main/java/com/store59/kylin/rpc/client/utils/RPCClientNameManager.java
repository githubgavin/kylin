/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.rpc.client.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 2.1 16/10/13
 * @since 2.1
 */
public class RPCClientNameManager {

    private static List<String> names = new ArrayList<>();

    public static void add(String name) {
        names.add(name);
    }

    public static List<String> getNames() {
        return names;
    }
}
