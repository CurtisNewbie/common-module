package com.curtisnewbie.common.async;

import brave.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;

import java.util.concurrent.*;
import java.util.function.*;

import static com.curtisnewbie.common.trace.TraceUtils.*;

/**
 * <p>
 * Event Pool that supports convenient async processing of disposable spring application event
 * </p>
 *
 * @author yongj.zhuang
 */
@Slf4j
public class EventPool {

    private final ExecutorService eventPool;

    @Autowired
    private Tracer tracer;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * Construct with default config: corePoolSize=4, maximumPoolSize=8, blockingQueue.capacity=500, DiscardPolicy
     */
    public EventPool() {
        this(4, 500, new ThreadPoolExecutor.DiscardPolicy());
    }

    public EventPool(int poolSize, int maxQueueSize, RejectedExecutionHandler rejectedExecutionHandler) {
        eventPool = new ThreadPoolExecutor(
                poolSize,
                poolSize,
                0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(maxQueueSize),
                rejectedExecutionHandler
        );
    }

    /**
     * Publish spring event asynchronously, if exception occurs, the exception will only be logged instead of being rethrown
     */
    public void publish(Object event) {
        submit(() -> eventPublisher.publishEvent(event));
    }

    // ------------------------------ private helper methods ---------------------

    private void submit(Runnable r, Consumer<Throwable> exceptionHandler) {
        try {
            CompletableFuture
                    .runAsync(() -> runWithSpan(r, tracer), eventPool)
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
        submit(r, e -> log.warn("EventPool.publishEvent error", e));
    }
}
