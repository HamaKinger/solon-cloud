package com.freedom.produce.controller;

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
    public String test2(){
        log.info("test2开始执行");
        test3();
        return "{\"message\":\"返回字符串并输出\"}";
    }
    public void test3(){
        log.info("test3!!!!");
        test4();
    }

    public void test4(){
        log.info("test4!!!!");
        test5();
    }
    public void test5(){
        log.info("test5!!!!");
        test6();
    }
    public void test6(){
        log.info("test6!!!!");
    }
}
