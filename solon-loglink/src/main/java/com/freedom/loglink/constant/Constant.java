package com.freedom.loglink.constant;

import com.google.common.base.Stopwatch;
import org.noear.solon.Solon;

/**
 * @author freedom
 * @description
 * @date 2024/3/9 18:05
 */
public interface Constant {
    int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors()+1 ;
    String TRACE_ID = "TRACE_ID";
    String PREFIX_LENGTH = "8";
    ThreadLocal<Stopwatch> STOPWATCH_THREAD = new ThreadLocal<>();
    String LENGTH = Solon.cfg().get("solon.logLink.length", PREFIX_LENGTH);
    String DEFAULT_PREFIX = Solon.cfg().get("solon.logLink.prefix","");
}
