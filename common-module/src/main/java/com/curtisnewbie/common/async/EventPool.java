package com.curtisnewbie.common.async;

import brave.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

import java.util.concurrent.*;
import java.util.function.*;

import static com.curtisnewbie.common.trace.TraceUtils.*;

/**
 * Event Pool that supports convenient async processing of disposable spring application event
 * <p>
 * See {@link #defaultExceptionHandler()} for default exception handler
 * <p>
 * See {@link #defaultExecutor()} and {@link #fixedExecutor(int, int)} for default executor service
 *
 * @author yongj.zhuang
 */
@Slf4j
public class EventPool {

    private final ExecutorService executorService;
    private final Consumer<Throwable> exceptionHandler;

    @Autowired
    private Tracer tracer;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public EventPool() {
        this(defaultExecutor(), defaultExceptionHandler());
    }

    public EventPool(ExecutorService executorService, Consumer<Throwable> exceptionHandler) {
        this.executorService = executorService;
        this.exceptionHandler = exceptionHandler;
    }

    public EventPool(Consumer<Throwable> exceptionHandler) {
        this(defaultExecutor(), exceptionHandler);
    }

    public EventPool(ExecutorService executorService) {
        this(executorService, defaultExceptionHandler());
    }

    /**
     * Publish spring event asynchronously, if exception occurs, the exception will only be logged instead of being
     * rethrown
     * <p>
     * Extra mechanism must be in place to ensure the all events are processed correctly, this event pool does not
     * provide such guarantee, e.g., having a scheduled job that compensates the errors
     */
    public void publish(Object event) {
        submit(() -> eventPublisher.publishEvent(event));
    }

    // ------------------------------ private helper methods ---------------------

    private void submit(Runnable r, Consumer<Throwable> exceptionHandler) {
        try {
            CompletableFuture
                    .runAsync(() -> runWithSpan(r, tracer), executorService)
                    .exceptionally(e -> {
                        if (exceptionHandler != null)
                            exceptionHandler.accept(e);
                        return null;
                    });
        } catch (Exception e) {
            log.warn("EventPool.submit error", e);
        }
    }

    private void submit(Runnable r) {
        submit(r, exceptionHandler);
    }

    /**
     * {@link ForkJoinPool#commonPool()}
     */
    public static ExecutorService defaultExecutor() {
        return ForkJoinPool.commonPool();
    }

    public static Consumer<Throwable> defaultExceptionHandler() {
        return e -> log.warn("EventPool.publishEvent error", e);
    }
}
