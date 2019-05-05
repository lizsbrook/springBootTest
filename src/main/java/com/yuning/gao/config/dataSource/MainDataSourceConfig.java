package com.yuning.gao.config.dataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: yf-wenhao
 * Date: 18/3/28
 * Time: 上午11:00
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.main")
@tk.mybatis.spring.annotation.MapperScan(basePackages = {"com.yuning.gao.dao.mapper.main"},
        sqlSessionFactoryRef = "sqlSessionFactoryMain")
public class MainDataSourceConfig extends BaseDataSourceConfig {


    //配置小程序后台数据源
    @Bean(name = "main")
    @Primary
    public DataSource dataSourceMain() {
        return createDataSource();
    }



    @Bean(name = "sqlSessionFactoryMain")
    @Primary
    public SqlSessionFactory sqlSessionFactoryMain() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSourceMain()); // 使用 lite  main 数据源, 连接前台库
        return factoryBean.getObject();
    }


    @Bean(name = "TransactionManagerMain")
    @Primary
    public DataSourceTransactionManager testTransactionManagerMain() {
        return new DataSourceTransactionManager(dataSourceMain());
    }


}

