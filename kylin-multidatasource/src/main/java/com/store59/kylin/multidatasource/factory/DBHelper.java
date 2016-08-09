/**
 * Copyright (c) 2016, 59store. All rights reserved.
 */
package com.store59.kylin.multidatasource.factory;

import com.alibaba.druid.pool.DruidDataSource;
import com.store59.kylin.multidatasource.db.DBLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collections;

/**
 * 连接池工具类
 *
 * @author <a href="mailto:zhongc@59store.com">士兵</a>
 * @version 1.0 16/7/14
 * @since 1.0
 */
public class DBHelper {

    private final static Logger LOGGER = LoggerFactory.getLogger(DBHelper.class);

    private final static String URL    = "jdbc:mysql://%s:%s/%s?" +
            "autoReconnect=true&useCompression=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";
    private final static String DRIVER = "com.mysql.jdbc.Driver";

    public static DruidDataSource buildDataSource(DBLink dbLink) {
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setUrl(String.format(URL, dbLink.getHost(), dbLink.getPort(), dbLink.getName()));
        dataSource.setUsername(dbLink.getUsername());
        dataSource.setPassword(dbLink.getPassword());
        dataSource.setDriverClassName(DRIVER);

        //以下为druid的优化参数
        dataSource.setMinIdle(dbLink.getMinIdle());               //最小连接池数量
        dataSource.setMaxActive(dbLink.getMaxActive());            //最大连接池数量
        dataSource.setMaxWait(dbLink.getMaxWait());            //获取连接时的最大等待时间, 单位毫秒. 配置了maxWait后, 缺省启用公平锁, 并发效率会有所下降, 如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
        dataSource.setInitialSize(dbLink.getInitialSize());
//        dataSource.setPoolPreparedStatements(false);    //是否缓存preparedStatement, mysql下建议关闭
//        dataSource.setMaxOpenPreparedStatements(-1);    //要启用PSSCache. 该配置必须大于0
        dataSource.setValidationQuery("SELECT 1");      //用于检测连接是否有效的sql
        dataSource.setValidationQueryTimeout(dbLink.getValidationQueryTimeout());        //单位：秒，检测连接是否有效的超时时间
        dataSource.setTestOnBorrow(true);               //申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
        dataSource.setTestOnReturn(false);              //归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
        dataSource.setTestWhileIdle(true);              //建议配置为true，不影响性能，并且保证安全性。
        dataSource.setMinEvictableIdleTimeMillis(dbLink.getMinEvictableIdleTimeMillis());
        dataSource.setRemoveAbandonedTimeout(dbLink.getRemoveAbandonedTimeout());
        dataSource.setConnectionInitSqls(Collections.singleton("set names utf8mb4"));

        try {
            dataSource.setFilters("wall, stat");
            dataSource.setUseGlobalDataSourceStat(true);
            dataSource.init();
        } catch (SQLException e) {
            LOGGER.error("load datasource error, dbProperties is :{}", e);
        }

        return dataSource;
    }
}
