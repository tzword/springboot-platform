package com.platform.common.dataSources;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author jianghy
 * @Description: 多数据源处理
 * @date 2020/4/26 13:13
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.platform.common.dataSources.DataSource)")
    public void dsPointCut() {

    }

    /**
     * @Description: 环绕
     * @param point 1 
     * @return java.lang.Object 
     * @throws
     * @author jianghy
     * @date 2020/4/26 13:15 
     */
    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();

        Method method = signature.getMethod();

        DataSource dataSource = method.getAnnotation(DataSource.class);

        if (null != dataSource) {
            DynamicDataSourceContextHolder.setDateSourceType(dataSource.value().name());
        }

        try {
            return point.proceed();
        } finally {
            // 销毁数据源 在执行方法之后
            DynamicDataSourceContextHolder.clearDateSourceType();
        }
    }
}