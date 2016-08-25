/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.rpc.utils.server.annotation;

import com.store59.kylin.rpc.context.annotation.Remote;
import com.store59.kylin.rpc.context.annotation.RemoteResource;
import org.springframework.core.annotation.AnnotationUtils;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 16/8/24
 * @since 1.0
 */
public class DemoService implements DemoApi {

    @RemoteResource
    DemoApi demoApi;

    public static void main(String[] args) {

//        test1();

        test2();

    }

    static void test1() {
        DemoService demoService = new DemoService();

        System.out.println(AnnotationUtils.findAnnotation(demoService.getClass(), Remote.class));

        System.out.println(AnnotationUtils.findAnnotationDeclaringClass(Remote.class, demoService.getClass()));

        for (Class inclass : demoService.getClass().getInterfaces()) {
            if (AnnotationUtils.isAnnotationDeclaredLocally(Remote.class, inclass)) {
                System.out.println(inclass);
            }
        }
    }

    static void test2() {
        DemoService demoService = new DemoService();

        System.out.println(AnnotationUtils.findAnnotation(demoService.getClass(), RemoteResource.class));

        System.out.println(AnnotationUtils.findAnnotationDeclaringClass(RemoteResource.class, demoService.getClass()));

        for (Class inclass : demoService.getClass().getInterfaces()) {
            if (AnnotationUtils.isAnnotationDeclaredLocally(RemoteResource.class, inclass)) {
                System.out.println(inclass);
            }
        }
    }

}
