package com.curtisnewbie.common.util;

import com.curtisnewbie.common.trace.TraceUtils;
import com.curtisnewbie.common.vo.*;
import lombok.extern.slf4j.*;
import org.springframework.web.context.request.async.*;

import java.util.concurrent.*;
import java.util.function.*;

/**
 * Async Utils
 *
 * @author yongj.zhuang
 */
@Slf4j
public final class AsyncUtils {

    private static final Executor defaultExecutor = ForkJoinPool.commonPool();
    private static final ConcurrentMap<String, Executor> executorMap = new ConcurrentHashMap<>();
    private static final int availableProcessors = Runtime.getRuntime().availableProcessors();

    static {
        log.info("Available Processors: {}", availableProcessors);
    }

    private AsyncUtils() {
    }

    /** Create work stealing pool with parallelism = N * processors */
    public static Executor workStealingPoolAvailProc(int n) {
        return workStealingPool(availableProcessors * n);
    }

    /** Create work stealing pool with parallelism = processors */
    public static Executor workStealingPoolAvailProc() {
        return workStealingPoolAvailProc(availableProcessors);
    }

    /** Create work stealing pool */
    public static Executor workStealingPool() {
        return Executors.newWorkStealingPool();
    }

    /** Create work stealing pool */
    public static Executor workStealingPool(int parallelism) {
        return Executors.newWorkStealingPool(parallelism);
    }

    /**
     * Get Executor by name
     * <p>
     * The executor is internally cached, if the executor is absent, a new working stealing pool is computed and
     * returned
     */
    public static Executor getExecutor(String name, int parallelism) {
        executorMap.computeIfAbsent(name, (key) -> Executors.newWorkStealingPool(parallelism));
        return executorMap.get(name);
    }

    public static DeferredResult<Result<Void>> runAsync(Runnable r) {
        return runAsyncResult(() -> {
            r.run();
            return null;
        });
    }

    public static DeferredResult<Result<Void>> runAsync(Runnable r, Executor executor) {
        return runAsyncResult(() -> {
            r.run();
            return null;
        }, executor);
    }

    public static <V> DeferredResult<Result<V>> runAsyncResult(Supplier<V> supplier) {
        return runAsync(() -> Result.of(supplier.get()));
    }

    public static <V> DeferredResult<Result<V>> runAsyncResult(Supplier<V> supplier, Executor executor) {
        return runAsync(() -> Result.of(supplier.get()), executor);
    }

    public static <V> DeferredResult<V> runAsync(Supplier<V> supplier) {
        return runAsync(supplier, defaultExecutor);
    }

    public static <V> DeferredResult<V> runAsync(Supplier<V> supplier, Executor executor) {
        if (executor == null) executor = defaultExecutor;

        final DeferredResult<V> dr = new DeferredResult<>();
        final TraceUtils.CurrentTrace currentTrace = TraceUtils.currentTrace();

        CompletableFuture.runAsync(() -> {
            currentTrace.tryRunWithSpan(() -> dr.setResult(supplier.get()));
        }, executor).exceptionally(e -> {
            dr.setErrorResult(e);
            return null;
        });
        return dr;
    }
}
