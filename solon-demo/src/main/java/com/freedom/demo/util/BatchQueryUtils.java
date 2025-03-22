package com.freedom.demo.util;

import com.freedom.loglink.utils.ThreadPoolUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @description:
 * @author: freedom
 * @date: 2025/3/20
 */
@Slf4j
public class BatchQueryUtils {

    public static <T,R> List<R> batchQuery(BatchQueryFunction<T,R> batchQueryFunction, List<T> t) throws Exception {
        ThreadPoolUtils poolUtils = ThreadPoolUtils.get();
        AtomicReference<List<R>> listAtomicReference = new AtomicReference<>(Lists.newArrayList());
        CompletableFuture.allOf(t.stream().map(id -> CompletableFuture.runAsync(() -> {
            R r = batchQueryFunction.accept(id);
            List<R> jsonObjects = listAtomicReference.get();
            jsonObjects.add(r);
            listAtomicReference.set(jsonObjects);
        },poolUtils).exceptionally(ex -> {
            log.error("Async operation failed",ex);
            throw new RuntimeException(ex);
        })).toArray(CompletableFuture[]::new)).join();
        log.info("list size: {}", listAtomicReference.get().size());
        shutdownThreadPool(poolUtils);
        return listAtomicReference.get();
    }

    public static void shutdownThreadPool(ThreadPoolUtils threadPool) {
        if (threadPool!= null) {
            threadPool.shutdown();
            try {
                if (!threadPool.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
                    threadPool.shutdownNow();
                    if (!threadPool.awaitTermination(60, java.util.concurrent.TimeUnit.SECONDS)) {
                        System.err.println("Pool did not terminate");
                    }
                }
            } catch (InterruptedException ie) {
                threadPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
