package com.freedom.loglink.utils;

import com.freedom.loglink.constant.Constant;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author: freedom
 * @date: 2023/3/31 16:30
 */
public final class ThreadMdcUtil {
    private ThreadMdcUtil(){
    }


    /**
     * 获取唯一性标识
     * {@link String}
     */
    public static String generateTraceId(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static void setTraceIdIfAbsent() {
        if (MDC.get(Constant.TRACE_ID) == null) {
            MDC.put(Constant.TRACE_ID, generateTraceId(Integer.parseInt(Constant.LENGTH)));
        }
    }

    /**
     * 用于父线程向线程池中提交任务时，将自身MDC中的数据复制给子线程
     * @author freedom
     * @date 2022/12/28 17:52
     * @param callable callable
     * @param context  context
     * @return {@link Callable<T>}
     */
    public static <T> Callable<T> wrap(final Callable<T> callable,final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }


    /**
     * 用于父线程向线程池中提交任务时，将自身MDC中的数据复制给子线程
     * @author freedom
     * @date 2022/12/28 17:52
     * @param runnable runnable
     * @param context  context
     * @return {@link Runnable}
     */
    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }
}
