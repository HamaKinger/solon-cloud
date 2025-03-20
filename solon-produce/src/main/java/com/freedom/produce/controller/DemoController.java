package com.freedom.produce.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.*;

/**
 * @description: test
 * @author: freedom
 * @date: 2024/7/6
 **/
@Controller
@Slf4j
public class DemoController {

    @Get
    @Mapping("/test2")
    public JSONObject test2(String id){
        log.info("test2开始执行");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        jsonObject.put("message","成功");
        return jsonObject;
    }
}
