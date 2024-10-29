package com.freedom.demo.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.freedom.loglink.annotaion.LogLink;
import com.freedom.loglink.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.*;
import org.noear.solon.boot.web.MimeType;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: test
 * @author: freedom
 * @date: 2024/7/6
 **/
@Controller
@Slf4j
public class DemoController {

    @Produces(MimeType.APPLICATION_JSON_VALUE)
    @Get
    @Mapping("/test2")
    @LogLink(prefix = "test2_",methodName = "test2",value = "")
    public String test2(){
        log.info("test2开始执行");
        return "{\"message\":\"返回字符串并输出\"}";
    }

    @Get
    @Mapping("/test1")
    public void test1(){
        String url ="http://localhost:8081/freedom-produce/test2" ;
        HttpRequest get = HttpUtil.createGet(url);
        HttpRequest post = HttpUtil.createPost(url);
        Map<String,String> headers = new HashMap<>();
        headers.put(Constant.TRACE_ID,MDC.get(Constant.TRACE_ID));
        get.addHeaders(headers);
        HttpResponse execute = get.execute();
        String body = execute.body();
        log.info("执行返回结果:{}",body);
    }
}
