package com.freedom.loglink.intercetpor;

import com.freedom.loglink.constant.Constant;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Filter;
import org.noear.solon.core.handle.FilterChain;
import org.slf4j.MDC;
import java.util.concurrent.TimeUnit;


/**
 * @description: 自定义过滤器
 * @author: freedom
 * @date: 2024/7/6
 **/
@Component
@Slf4j
public class LogFilter implements Filter {
    @Override
    public void doFilter (Context ctx,FilterChain chain) throws Throwable {
        Stopwatch started = Stopwatch.createStarted();
        Constant.STOPWATCH_THREAD.set(started);
        before(ctx);
        //执行处理
        chain.doFilter(ctx);
        after(ctx);
    }

    private void before (Context ctx) {
        String tid = ctx.header(Constant.TRACE_ID);
        //header不存在
        if(StringUtils.isEmpty(tid)){
            //设置长度
            tid = Constant.DEFAULT_PREFIX+RandomStringUtils.randomAlphanumeric(Integer.parseInt(Constant.LENGTH));
        }
        MDC.put(Constant.TRACE_ID,tid);
    }

    public void after (Context ctx) {
        try {
            Stopwatch stopwatch = Constant.STOPWATCH_THREAD.get();
            if (stopwatch == null) {
                return;
            }
            long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
            log.info("接口:{} , 请求耗时:{} ms", ctx.url(),elapsed);
        }finally {
            Constant.STOPWATCH_THREAD.remove();
            MDC.remove(Constant.TRACE_ID);
        }
    }
}
