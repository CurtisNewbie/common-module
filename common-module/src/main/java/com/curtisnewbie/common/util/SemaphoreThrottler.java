package com.curtisnewbie.common.util;

import com.curtisnewbie.common.exceptions.*;
import org.springframework.util.*;

import java.util.concurrent.*;

/**
 * Throttler based on Semaphore
 *
 * @author yongj.zhuang
 */
public class SemaphoreThrottler {

    private final Semaphore semaphore;

    public SemaphoreThrottler(int maxConcurrency) {
        Assert.isTrue(maxConcurrency > 0, "maxConcurrency must be greater than 0");
        this.semaphore = new Semaphore(maxConcurrency);
    }


    /**
     * Queue the action, if maxConcurrency is exceeded, current thread will wait indefinitely
     *
     * @throws OperationAbortedException if the queuing was interrupted
     */
    public <T> T queue(Callable<T> callable) {
        return queue(callable, -1, null);
    }

    /**
     * Queue the action, if maxConcurrency is exceeded, current thread will wait until it times out
     *
     * @throws OperationAbortedException if the queuing was timed-out or interrupted
     */
    public <T> T queue(Callable<T> callable, long timeout, TimeUnit timeUnit) {
        boolean isAcquired = false;
        try {
            if (timeout < 0) {
                semaphore.acquire();
                isAcquired = true;
            } else {
                isAcquired = semaphore.tryAcquire(timeout, timeUnit);
            }
            if (!isAcquired) throw new OperationAbortedException("Unable to acquire semaphore, aborted");
            return Runner.tryCall(callable);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // reset interrupted
            throw new OperationAbortedException(e);
        } finally {
            if (isAcquired) semaphore.release();
        }
    }

}
