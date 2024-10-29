package com.freedom.produce.task;

import com.freedom.loglink.annotaion.LogLink;
import lombok.extern.slf4j.Slf4j;
import org.noear.solon.annotation.Component;
import org.noear.solon.scheduling.annotation.Scheduled;

/**
 * @description: 定时任务
 * @author: freedom
 * @date: 2024/7/7
 **/
@Component
@Slf4j
public class TaskDemo {

    @Scheduled(cron = "0/50 * * * * ? *")
    @LogLink(methodName = "job13",value = "定时任务执行每隔50s",prefix = "job13_")
    public void job13(){
        log.info("我是 job13 （0/50 * * * * ? *）");
    }

    @Scheduled(cron = "0/30 * * * * ? *")
    @LogLink(methodName = "job14",value = "定时任务执行每隔30s",prefix = "job14_")
    public void job14(){
        log.info("我是 job14 （0/30 * * * * ? *）");
    }
}
