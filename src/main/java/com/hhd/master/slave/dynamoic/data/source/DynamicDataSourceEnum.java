package com.hhd.master.slave.dynamoic.data.source;

/**
 * Created by kongling on 2020/5/26 14:02
 */
public enum DynamicDataSourceEnum {

    MASTER("master"),
    SLAVE("slave");

    private String dataSourceName;

    DynamicDataSourceEnum(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }
}
