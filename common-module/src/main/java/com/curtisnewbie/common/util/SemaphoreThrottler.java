package com.curtisnewbie.common.util;

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
     * Queue the action
     */
    public <T> T queue(Callable<T> callable) {
        try {
            return queue(callable, -1, null);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Queue the action
     *
     * @throws InterruptedException if queuing was timed-out
     */
    public <T> T queue(Callable<T> callable, long timeout, TimeUnit timeUnit) throws InterruptedException {
        boolean isAcquired = false;
        try {
            if (timeout == -1) {
                semaphore.acquire();
                isAcquired = true;
            } else {
                isAcquired = semaphore.tryAcquire(timeout, timeUnit);
            }
            return Runner.tryCall(callable);
        } finally {
            if (isAcquired) semaphore.release();
        }
    }

}
