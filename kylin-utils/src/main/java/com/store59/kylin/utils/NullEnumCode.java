/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.utils;

/**
 * Null枚举码对象.
 * 
 * @author <a href="mailto:zhuzm@59store.com">天河</a>
 * @version 1.0 2016年3月3日
 * @since 1.0
 */
public class NullEnumCode<C> implements EnumCode<C> {

    /**
     * 无参构造.
     */
    private NullEnumCode() {
        super();
    }

    /** 枚举码为{@link String}类型的{@link NullEnumCode}对象. */
    public static final EnumCode<String>  STRING  = new NullEnumCode<String>();

    /** 枚举码为{@link Integer}类型的{@link NullEnumCode}对象. */
    public static final EnumCode<Integer> INTEGER = new NullEnumCode<Integer>();

    /** 枚举码为{@link Byte}类型的{@link NullEnumCode}对象. */
    public static final EnumCode<Byte>    BYTE    = new NullEnumCode<Byte>();

    /**
     * @see com.store59.kylin.utils.EnumCode#getCode()
     */
    @Override
    public C getCode() {
        return null;
    }

}
