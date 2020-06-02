package com.hhd.master.slave.dynamoic.data.source;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by kongling on 2020/5/26 11:54
 */
public class DynamicDataSource  extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.get();
    }
}
