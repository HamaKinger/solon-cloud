package com.freedom.loglink.intercetpor;

import com.freedom.loglink.annotaion.LogLink;
import com.freedom.loglink.constant.Constant;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.aspect.Interceptor;
import org.noear.solon.core.aspect.Invocation;
import org.slf4j.MDC;

import java.util.concurrent.TimeUnit;


/**
 * @description: 日志拦截器
 * @author: freedom
 * @date: 2024/7/6
 **/
@Component
@Slf4j
public class LogLinkInterceptor implements Interceptor  {

    @Override
    public Object doIntercept(Invocation inv) throws Throwable {
        Stopwatch started = Stopwatch.createStarted();
        Constant.STOPWATCH_THREAD.set(started);
        String traceId = MDC.get(Constant.TRACE_ID);
        if(StringUtils.isEmpty(traceId)){
            try {
                before(inv);
                return inv.invoke();
            }finally {
                after(inv);
            }
        }else {
            after(inv);
            return inv.invoke();
        }
    }

    public void after(Invocation inv) {
        try {
            Stopwatch stopwatch = Constant.STOPWATCH_THREAD.get();
            LogLink logLink = inv.method().getAnnotation(LogLink.class);
            if (stopwatch == null) {
                return;
            }
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            log.info("{};方法:{}请求耗时:{} ms", logLink.value(),logLink.methodName(),elapsed);
        }finally {
            Constant.STOPWATCH_THREAD.remove();
            MDC.remove(Constant.TRACE_ID);
        }
    }

    private static void before (Invocation inv) {
        LogLink logLink = inv.method().getAnnotation(LogLink.class);
        String prefix = logLink.prefix();
        //MDC链路日志配置
        String tid = RandomStringUtils.randomAlphanumeric(Integer.parseInt(Constant.LENGTH));
        //利用MDC将请求存储
        MDC.put(Constant.TRACE_ID,prefix==null?"":prefix+tid);
    }
}
