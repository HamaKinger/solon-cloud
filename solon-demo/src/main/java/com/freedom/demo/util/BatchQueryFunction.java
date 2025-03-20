package com.freedom.demo.util;

/**
 * @description:
 * @author: freedom
 * @date: 2025/3/20
 */
@FunctionalInterface
public interface BatchQueryFunction<T,R> {

    R accept(T t);
}
