package com.freedom.demo.task;

import com.freedom.loglink.annotaion.LogLink;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.Component;
import org.noear.solon.scheduling.annotation.Scheduled;

/**
 * @description: 定时任务
 * @author: freedom
 * @date: 2024/7/7
 **/
//@Component
@Slf4j
public class TaskDemo {
    @Scheduled(cron = "0 0/1 * * * ? *")
    @LogLink(methodName = "job12",value = "定时任务执行每隔1分钟",prefix = "job12_")
    public void job12(){
        log.info("我是 job12 （0/10 * * * * ? *）");
    }

    @Scheduled(cron = "0/10 * * * * ? *")
    @LogLink(methodName = "job13",value = "定时任务执行每隔10s",prefix = "job13_")
    public void job13(){
        log.info("我是 job13 （0/10 * * * * ? *）");
    }

    @Scheduled(cron = "0 0/10 * * * ? *")
    @LogLink(methodName = "job14",value = "定时任务执行每隔10分钟",prefix = "job14_")
    public void job14(){
        log.info("我是 job14 （0/10 * * * * ? *）");
    }
}
