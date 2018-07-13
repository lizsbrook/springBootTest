package com.shangde.gao.config.dataSource;

import lombok.Data;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 10:23 2018/7/13
 * Description：${description}
 */
@Data
public class BaseDataSourceConfig {

    private String url;

    private String username;

    private String password;

    public DataSource createDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername(getUsername());
        basicDataSource.setUrl(getUrl());
        basicDataSource.setPassword(getPassword());
        //<!-- 连接池启动时创建的初始化连接数量（默认值为0） -->
        basicDataSource.setInitialSize(1);
        //<!-- 连接池中可同时连接的最大的连接数 默认 8-->
        basicDataSource.setMaxTotal(200);
        // 连接池中最小的空闲的连接数，低于这个数量会被创建新的连接
        basicDataSource.setMinIdle(20);
        // 连接池中最大的空闲的连接数，超过的空闲连接将被释放，如果设置为负数表示不限制 默认8 -->
        basicDataSource.setMaxIdle(100);
        //超时
        basicDataSource.setMaxWaitMillis(60000);
        //超过时间限制，回收没有用(废弃)的连接（默认为 300秒，调整为180）
        basicDataSource.setRemoveAbandonedTimeout(180);
        basicDataSource.setDefaultAutoCommit(true);
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return basicDataSource;
    }

}
