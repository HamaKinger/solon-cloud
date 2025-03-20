package com.freedom.loglink.utils;

import com.freedom.loglink.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.util.concurrent.*;

/**
 * @description: 本地线程池
 * @author: freedom
 * @date: 2024/7/6
 **/
@Slf4j
public class ThreadPoolUtils extends ThreadPoolExecutor{


    public ThreadPoolUtils (int corePoolSize,int maximumPoolSize,long keepAliveTime,
                            TimeUnit unit,BlockingQueue<Runnable> workQueue) {
        super(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue);
    }

    public ThreadPoolUtils (int corePoolSize,int maximumPoolSize,long keepAliveTime,
                            TimeUnit unit,BlockingQueue<Runnable> workQueue,
                            ThreadFactory threadFactory) {
        super(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,threadFactory);
    }

    public ThreadPoolUtils (int corePoolSize,int maximumPoolSize,long keepAliveTime,
                            TimeUnit unit,BlockingQueue<Runnable> workQueue,
                            RejectedExecutionHandler handler) {
        super(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,handler);
    }

    public ThreadPoolUtils (int corePoolSize,int maximumPoolSize,long keepAliveTime,
                            TimeUnit unit,BlockingQueue<Runnable> workQueue,
                            ThreadFactory threadFactory,RejectedExecutionHandler handler) {
        super(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,threadFactory,handler);
    }

    public static ThreadPoolUtils get(){
        return new ThreadPoolUtils(Constant.CORE_POOL_SIZE,
                Constant.CORE_POOL_SIZE*2,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                (r,executor) -> log.info("达到线程池处理能力上限, 忽略此任务"));
    }


    public static ThreadPoolUtils get(Integer queueSize){
        return new ThreadPoolUtils(Constant.CORE_POOL_SIZE,
                Constant.CORE_POOL_SIZE*2,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueSize),(r,executor) -> log.info("忽略此任务"));
    }

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }


    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }
}
