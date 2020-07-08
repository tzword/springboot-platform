package com.platform.common.sign;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jianghy
 * @Description: 签名注解类
 * @date 2020/6/28 11:00
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SginAnot {
    SginEnum type() default SginEnum.ANY;//默认不需要签名
}

