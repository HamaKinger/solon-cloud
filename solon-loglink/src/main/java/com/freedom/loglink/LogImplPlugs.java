package com.freedom.loglink;

import lombok.extern.slf4j.Slf4j;
import org.noear.solon.core.AppContext;
import org.noear.solon.core.Plugin;

/**
 * @description: 日志插件
 * @author: freedom
 * @date: 2024/7/7
 **/
@Slf4j
public class LogImplPlugs implements Plugin {

    @Override
    public void start (AppContext context) {
        context.beanScan("com.freedom.loglink");
    }

    @Override
    public void prestop () throws Throwable {
        Plugin.super.prestop();
    }

    @Override
    public void stop () throws Throwable {
        Plugin.super.stop();
    }
}
