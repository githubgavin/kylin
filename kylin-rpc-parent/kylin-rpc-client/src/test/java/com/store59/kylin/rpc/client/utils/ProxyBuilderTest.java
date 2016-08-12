package com.store59.kylin.rpc.client.utils;

import com.store59.exmaple.rpc.remoting.MsgService;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 16/7/21
 * @since 1.0
 */
public class ProxyBuilderTest {

    MsgService msgService;

    @Before
    public void setUp() throws Exception {
        msgService = ProxyBuilder.create().useHttpClient().setServiceUrl("http://192.168.30.40:7101/examplerpcservice")
                .setServiceExportName("msg").setInterfaceClass(MsgService.class).build();
    }

    @Test
    public void build() throws Exception {
        for (int i =0 ;i< 100;i++) {
            msgService.getAllMsgList();
        }
    }

}
