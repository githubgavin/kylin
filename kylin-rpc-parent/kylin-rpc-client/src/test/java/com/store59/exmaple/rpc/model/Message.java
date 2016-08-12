/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.exmaple.rpc.model;

import java.io.Serializable;

/**
 * @author <a href="mailto:chenyb@59store.com">山人</a>
 * @version 1.0 16/4/22
 * @since 1.0
 */
public class Message implements Serializable {

    private Integer id;
    private String  title;
    private String  content;
    private String  createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
