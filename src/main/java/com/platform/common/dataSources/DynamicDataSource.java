package com.platform.common.dataSources;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;


/**
 * @author jianghy
 * @Description: 动态数据源
 * @date 2020/4/26 10:54
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    public DynamicDataSource(javax.sql.DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDateSourceType();
    }
}