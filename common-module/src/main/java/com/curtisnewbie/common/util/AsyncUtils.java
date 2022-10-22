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

    public static final int AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    /** Lazy init work stealing pool */
    private static volatile Executor commonWorkStealingPool;
    /** Lock for {@link #commonWorkStealingPool} */
    private static final Object cwspLock = new Object();

    static {
        log.info("Available Processors: {}", AVAILABLE_PROCESSORS);
    }

    private AsyncUtils() {
    }

    public static Executor getCommonWorkStealingPool() {
        if (commonWorkStealingPool != null)
            return commonWorkStealingPool;

        synchronized (cwspLock) {
            if (commonWorkStealingPool == null) {
                commonWorkStealingPool = workStealingPool(AVAILABLE_PROCESSORS * 2);
            }
        }

        return commonWorkStealingPool;
    }

    /** Create work stealing pool with parallelism = processors */
    public static Executor workStealingPoolWithAvailProc() {
        return workStealingPool(AVAILABLE_PROCESSORS);
    }

    /** Create work stealing pool */
    public static Executor workStealingPool() {
        return Executors.newWorkStealingPool();
    }

    /** Create work stealing pool */
    public static Executor workStealingPool(int parallelism) {
        return Executors.newWorkStealingPool(parallelism);
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
        return runAsync(supplier, getCommonWorkStealingPool());
    }

    public static <V> DeferredResult<V> runAsync(Supplier<V> supplier, Executor executor) {
        if (executor == null) executor = getCommonWorkStealingPool();

        final DeferredResult<V> dr = new DeferredResult<>();
        final TraceUtils.CurrentTrace currentTrace = TraceUtils.currentTrace();

        CompletableFuture.runAsync(() -> currentTrace.tryRunWithSpan(() -> dr.setResult(supplier.get())), executor)
                .exceptionally(e -> {
                    dr.setErrorResult(e);
                    return null;
                });
        return dr;
    }
}
