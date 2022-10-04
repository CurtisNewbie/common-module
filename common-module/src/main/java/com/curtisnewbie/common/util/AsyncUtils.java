package com.curtisnewbie.common.util;

import brave.*;
import com.curtisnewbie.common.trace.TraceUtils;
import com.curtisnewbie.common.vo.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.stereotype.*;
import org.springframework.web.context.request.async.*;

import java.util.concurrent.*;
import java.util.function.*;

/**
 * Async Utils (Tracer aware, do not try to instantiate this bean)
 *
 * @author yongj.zhuang
 */
@Slf4j
public class AsyncUtils {

    public static DeferredResult<Result<Void>> runAsync(Runnable r) {
        return runAsyncResult(() -> {
            r.run();
            return null;
        });
    }

    public static <V> DeferredResult<Result<V>> runAsyncResult(Supplier<V> supplier) {
        return runAsync(() -> Result.of(supplier.get()));
    }

    public static <V> DeferredResult<V> runAsync(Supplier<V> supplier) {
        final DeferredResult<V> dr = new DeferredResult<>();
        final TraceUtils.CurrentTrace currentTrace = TraceUtils.currentTrace();

        CompletableFuture.runAsync(() -> {
            currentTrace.tryRunWithSpan(() -> dr.setResult(supplier.get()));
        }).exceptionally(e -> {
            dr.setErrorResult(e);
            return null;
        });
        return dr;
    }
}
