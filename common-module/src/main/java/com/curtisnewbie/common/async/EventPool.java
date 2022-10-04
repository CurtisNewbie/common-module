package com.curtisnewbie.common.async;

import brave.*;
import lombok.extern.slf4j.*;
import org.springframework.context.*;
import org.springframework.lang.*;
import org.springframework.util.*;

import java.util.concurrent.*;
import java.util.function.*;

import static com.curtisnewbie.common.trace.TraceUtils.*;

/**
 * Event Pool that supports convenient async processing of disposable spring application event
 * <p>
 * See {@link #defaultExceptionHandler()} for default exception handler
 * <p>
 * See {@link #defaultExecutor()} for default executor service
 *
 * @author yongj.zhuang
 */
@Slf4j
public class EventPool {

    private final ExecutorService executorService;
    private final Consumer<Throwable> exceptionHandler;
    private final ApplicationEventPublisher eventPublisher;

    @Nullable
    private final Tracer tracer;

    public EventPool(ApplicationEventPublisher applicationEventPublisher, @Nullable Tracer tracer) {
        this(defaultExecutor(), defaultExceptionHandler(), applicationEventPublisher, tracer);
    }

    public EventPool(ExecutorService executorService, Consumer<Throwable> exceptionHandler,
                     ApplicationEventPublisher applicationEventPublisher,
                     @Nullable Tracer tracer) {

        Assert.notNull(applicationEventPublisher, "ApplicationEventPublisher is null");
        if (executorService == null) executorService = defaultExecutor();
        if (exceptionHandler == null) exceptionHandler = defaultExceptionHandler();

        this.executorService = executorService;
        this.exceptionHandler = exceptionHandler;
        this.tracer = tracer;
        this.eventPublisher = applicationEventPublisher;
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

    private void submit(Runnable r) {
        try {
            CompletableFuture
                    .runAsync(() -> {
                        if (tracer != null) runWithSpan(r, tracer);
                        else r.run();
                    }, executorService)
                    .exceptionally(e -> {
                        if (exceptionHandler != null) exceptionHandler.accept(e);
                        return null;
                    });
        } catch (Exception e) {
            log.warn("EventPool.submit error", e);
        }
    }

    /**
     * Default Executor Service (internally, it creates a WorkStealingPool, parallelism = number_of_processors )
     */
    public static ExecutorService defaultExecutor() {
        return Executors.newWorkStealingPool();
    }

    public static Consumer<Throwable> defaultExceptionHandler() {
        return e -> log.warn("EventPool.publishEvent error", e);
    }
}
