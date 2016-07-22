/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.exmaple.rpc.remoting;

import com.store59.exmaple.rpc.model.Message;

import java.util.List;

/**
 *
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 16/4/22
 * @since 1.0
 */
public interface MsgService {

    List<Message> getAllMsgList();

    boolean addMsg(Message message);

    boolean delMsg(Integer msgId);
}
