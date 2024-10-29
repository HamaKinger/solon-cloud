package com.freedom.loglink.config;

import com.freedom.loglink.constant.Constant;
import com.freedom.loglink.utils.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.aspect.Invocation;
import org.noear.solon.scheduling.annotation.Async;
import org.noear.solon.scheduling.async.AsyncExecutor;

import java.util.concurrent.*;

/**
 * @description: 异步执行器
 * @author: freedom
 * @date: 2024/7/7
 **/
public class AsyncExecutorImpl implements AsyncExecutor {
    public static final ThreadPoolUtils LOCAL_THREAD_POOL_UTILS = ThreadPoolUtils.get(1000);

    @Override
    public Future submit(Invocation inv,Async anno) throws Throwable{
        if (inv.method().getReturnType().isAssignableFrom(Future.class)) {
            return (Future) inv.invoke();
        } else {
            return LOCAL_THREAD_POOL_UTILS.submit(() -> {
                try {
                    return inv.invoke();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
