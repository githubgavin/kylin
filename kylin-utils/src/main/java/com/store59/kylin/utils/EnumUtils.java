/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.store59.kylin.utils;

import org.apache.commons.lang3.ObjectUtils;

/**
 * 枚举工具类.
 * 
 * @author <a href="mailto:zhuzm@59store.com">天河</a>
 * @version 1.0 2015年12月30日
 * @since 1.0
 */
public abstract class EnumUtils {

    /**
     * 根据枚举类型和枚举码获取枚举对象.
     * 
     * @param enumClass 枚举类型
     * @param code 枚举码
     * @return 枚举对象
     */
    public static <E extends Enum<E> & EnumCode<T>, T> E getEnumByCode(Class<E> enumClass, T code) {
        if (code == null) {
            return null;
        }

        for (E each : enumClass.getEnumConstants()) {
            if (ObjectUtils.equals(code, each.getCode())) {
                return each;
            }
        }

        return null;
    }

    /**
     * <p>
     * Gets the enum for the class, returning {@code null} if not found.
     * </p>
     *
     * <p>
     * This method differs from {@link Enum#valueOf} in that it does not throw an exception for an invalid enum name.
     * </p>
     *
     * @param <E> the type of the enumeration
     * @param enumClass the class of the enum to query, not null
     * @param enumName the enum name, null returns null
     * @return the enum, null if not found
     */
    public static <E extends Enum<E>> E getEnum(Class<E> enumClass, String enumName) {
        if (enumName == null) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, enumName);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

}
