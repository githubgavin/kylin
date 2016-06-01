/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.utils;

import java.util.Random;

/**
 * 主键 唯一键生成
 *
 * @author <a href="mailto:zhongc@59store.com">士兵</a>
 * @version 1.0 16/4/11
 * @since 1.0
 */
public class CommonKeyUtils {

    //2016-01-01的时间戳
    private final static long twepoch = 1451577600446L;

    // 毫秒内自增位
    private final static long sequenceBits = 12L;

    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);

    private static long lastTimestamp = -1L;

    private static long sequence = 0L;

    public static Random random = new Random();

    //随机数序列
    private final static int randBits = 10;

    private static int rand = 0;

    private static int randMask =  -1 ^ (-1 << randBits);

    /**
     * 生成主键key
     *
     * 时间戳(42位) + 序列(12位) + 随机数(10位)
     *
     * @return
     */
    public static synchronized String genUniqueKey() {
        long timestamp = timeGen();
        if(timestamp < lastTimestamp) {
            try {
                throw new Exception("Clock moved backwards.  Refusing to generate id for "+ (lastTimestamp - timestamp) + " milliseconds");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }

        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;

        rand = random.nextInt(randMask);

        return new Long(((timestamp - twepoch) << 22) | sequence | rand).toString();
    }

    private static long tilNextMillis(final long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private static long timeGen() {
        return System.currentTimeMillis();
    }
}
