/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 金额枚举
 *
 * @author <a href="mailto:zhongc@59store.com">士兵</a>
 * @version 1.0 16/8/24
 * @since 1.0
 */
public enum MoneyUnit {

    YUAN {
        public Integer toFen(Double d) {
            if(d == null)
                return 0;
            return new BigDecimal(d).setScale(2, RoundingMode.HALF_UP).movePointRight(2).intValue();
        }
    },

    FEN {
        public Double toYuan(Integer d) {
            if(d == null)
                return 0d;
            return new BigDecimal(d).movePointLeft(2).doubleValue();
        }
    }

    ;

    public Integer toFen(Double d) {
        throw new AbstractMethodError();
    }

    public Double toYuan(Integer d) {
        throw new AbstractMethodError();
    }
}
