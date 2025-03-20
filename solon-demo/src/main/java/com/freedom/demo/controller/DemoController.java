package com.freedom.demo.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.freedom.loglink.annotaion.LogLink;
import com.freedom.loglink.constant.Constant;
import com.freedom.loglink.utils.ThreadPoolUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.*;
import org.noear.solon.boot.web.MimeType;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @description: test
 * @author: freedom
 * @date: 2024/7/6
 **/
@Controller
@Slf4j
public class DemoController {
    public static final ThreadPoolUtils threadPoolUtils = ThreadPoolUtils.get();

    @Produces(MimeType.APPLICATION_JSON_VALUE)
    @Get
    @Mapping("/test2")
    @LogLink(prefix = "test2_", methodName = "test2", value = "")
    public String test2 () {
        log.info("test2开始执行");
        return "{\"message\":\"返回字符串并输出\"}";
    }

    @Post
    @Mapping("/test1")
    public void test1 (@Body List<String> ids) throws ExecutionException, InterruptedException {
        AtomicReference<List<JSONObject>> listAtomicReference = new AtomicReference<>(Lists.newArrayList());
        List<CompletableFuture<Void>> futures = ids.stream().map(id -> CompletableFuture.runAsync(() -> {
            String url = "http://localhost:8081/freedom-produce/test2" + "?id=" + id;
            HttpRequest get = HttpUtil.createGet(url);
            Map<String,String> headers = new HashMap<>();
            headers.put(Constant.TRACE_ID,MDC.get(Constant.TRACE_ID));
            get.addHeaders(headers);
            HttpResponse execute = get.execute();
            String body = execute.body();
            log.info("执行返回结果:{}",body);
            JSONObject jsonObject = JSON.parseObject(body);
            List<JSONObject> jsonObjects = listAtomicReference.get();
            jsonObjects.add(jsonObject);
            listAtomicReference.set(jsonObjects);
        },threadPoolUtils)).toList();
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenRun(() -> log.info("list:{}", listAtomicReference.get()))
                .get();
    }

}
