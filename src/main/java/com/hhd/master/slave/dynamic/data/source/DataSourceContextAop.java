package com.hhd.master.slave.dynamic.data.source;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by kongling on 2020/5/26 14:38
 */
@Slf4j
@Aspect
@Order(1)
@Component
public class DataSourceContextAop {

    @Around("@annotation(com.hhd.master.slave.dynamic.data.source.DataSourceSelector) || @within(com.hhd.master.slave.dynamic.data.source.DataSourceSelector)")
    public Object setDataSource(ProceedingJoinPoint point) throws Throwable {
        boolean clear = true;
        try {
            Class<?> clazz = point.getTarget().getClass();
            DataSourceSelector dataSourceSelector = clazz.getAnnotation(DataSourceSelector.class);
            if (dataSourceSelector != null) {
                clear = dataSourceSelector.clear();
                DataSourceContextHolder.set(dataSourceSelector.value().name());
                log.info("class change, data source={}", dataSourceSelector.value().name());
            }
            dataSourceSelector = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(DataSourceSelector.class);
            if (dataSourceSelector!=null ){
                clear = dataSourceSelector.clear();
                DataSourceContextHolder.set(dataSourceSelector.value().name());
                log.info("method change, data source={}", dataSourceSelector.value().name());
            }
            return point.proceed();
        } finally {
            if (clear) {
                DataSourceContextHolder.claer();
            }
        }
    }
}
