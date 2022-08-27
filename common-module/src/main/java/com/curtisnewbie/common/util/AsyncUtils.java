package com.curtisnewbie.common.util;

import brave.*;
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
@Component
public class AsyncUtils implements ApplicationContextAware {

    private static volatile Tracer tracer = null;

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
        final Span span = tracer != null ? tracer.currentSpan() : null;

        CompletableFuture.runAsync(() -> {
            if (span != null) {
                try (Tracer.SpanInScope ws = tracer.withSpanInScope(span)) {
                    dr.setResult(supplier.get());
                }
            } else {
                dr.setResult(supplier.get());
            }
        }).exceptionally(e -> {
            dr.setErrorResult(e);
            return null;
        });
        return dr;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            AsyncUtils.tracer = applicationContext.getBean(Tracer.class);
        } catch (Exception e) {
            log.error("Failed to obtain Tracer from ApplicationContext", e);
        }
    }
}
