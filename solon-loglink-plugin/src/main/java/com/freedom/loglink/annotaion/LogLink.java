package com.freedom.loglink.annotaion;

import com.freedom.loglink.intercetpor.LogLinkInterceptor;
import org.noear.solon.annotation.Around;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author freedom
 * @description
 * @date 2024/3/9 17:47
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Around(LogLinkInterceptor.class)
public @interface LogLink {

    String value () default "";
    String prefix () default "";
    String methodName () default "" ;
}
