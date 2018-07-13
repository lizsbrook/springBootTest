package com.shangde.gao.config.dataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 10:42 2018/7/13
 * Description：${description}
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.admin")
@tk.mybatis.spring.annotation.MapperScan(basePackages = {"com.shangde.gao.dao.mapper.admin"},
        sqlSessionFactoryRef = "sqlSessionFactoryAdmin")
public class AdminDataSourceConfig extends BaseDataSourceConfig{
    //配置抹茶证书后台数据源
    @Bean(name = "admin")
    public DataSource dataSourceAdmin() {
        return createDataSource();
    }

    @Bean(name = "sqlSessionFactoryAdmin")
    public SqlSessionFactory sqlSessionFactoryAdmin() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSourceAdmin());
        return factoryBean.getObject();
    }


    @Bean(name = "TransactionManagerAdmin")
    public DataSourceTransactionManager testTransactionManagerAdmin() {
        return new DataSourceTransactionManager(dataSourceAdmin());
    }

}
