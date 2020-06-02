package com.hhd.master.slave.dynamic.data.source;

/**
 * Created by kongling on 2020/5/26 11:55
 */
public class DataSourceContextHolder {
    private static final ThreadLocal<String> DYNAMIC_DATA_SOURCE_CONTEXT = new ThreadLocal<>();

    public static void set(String dataSourceType) {
        DYNAMIC_DATA_SOURCE_CONTEXT.set(dataSourceType);
    }

    public static String get(){
        return DYNAMIC_DATA_SOURCE_CONTEXT.get();
    }

    public static void claer() {
        DYNAMIC_DATA_SOURCE_CONTEXT.remove();
    }
}
