package com.curtisnewbie.common.util;

import com.curtisnewbie.common.vo.*;
import org.springframework.web.context.request.async.*;

import java.util.concurrent.*;
import java.util.function.*;

/**
 * Async Utils
 *
 * @author yongj.zhuang
 */
public final class AsyncUtils {

    private AsyncUtils() {
    }

    public static <V> DeferredResult<Result<V>> ofAsyncResult(Supplier<V> supplier) {
        return runAsync(() -> Result.of(supplier.get()));
    }

    public static <V> DeferredResult<V> runAsync(Supplier<V> supplier) {
        final DeferredResult<V> dr = new DeferredResult<>();

        CompletableFuture.runAsync(() -> {
            dr.setErrorResult(supplier.get());
        }).exceptionally(e -> {
            dr.setErrorResult(e);
            return null;
        });
        return dr;
    }
}
