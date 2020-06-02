package com.hhd.master.slave.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.hhd.master.slave.dynamoic.data.source.DynamicDataSource;
import com.hhd.master.slave.dynamoic.data.source.DynamicDataSourceEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kongling on 2020/5/26 16:07
 */
@Configuration
@MapperScan(basePackages = "com.hhd.master.slave.mapper", sqlSessionTemplateRef = "sqlTemplate")
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix="dynamic.datasource.master")
    public DataSource masterDb() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix="dynamic.datasource.slave")
    public DataSource slaveDb() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public DynamicDataSource dynamicDataSource(@Qualifier("masterDb") DataSource master, @Autowired(required = false) @Qualifier("slaveDb") DataSource slave) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object,Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSourceEnum.MASTER.getDataSourceName(),master);
        if (slave != null) {
            targetDataSources.put(DynamicDataSourceEnum.SLAVE.getDataSourceName(),slave);
        }
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(master);
        return dynamicDataSource;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dynamicDataSource);
        return dataSourceTransactionManager;
    }

    @Bean
    public SqlSessionFactory sessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*Mapper.xml"));
        bean.setDataSource(dynamicDataSource);
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlTemplate(@Qualifier("sessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
