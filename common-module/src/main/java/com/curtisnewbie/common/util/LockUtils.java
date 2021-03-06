package com.curtisnewbie.common.util;

import org.springframework.util.*;

import java.util.concurrent.locks.*;

/**
 * Utils for Lock
 *
 * @author yongj.zhuang
 */
public final class LockUtils {

    private LockUtils() {

    }

    /**
     * Lock then run
     */
    public static void lockAndRunThrowable(Lock lock, ThrowableRunnable r) throws Exception {
        Assert.notNull(lock, "lock == null");
        Assert.notNull(r, "throwableRunnable == null");

        lock.lock(); // if this throws exception, we didn't get the lock at all, then we don't need to unlock
        try {
            r.run();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Lock then run
     */
    public static void lockAndRun(Lock lock, Runnable r) {
        Assert.notNull(lock, "lock == null");
        Assert.notNull(r, "runnable == null");

        lock.lock(); // if this throws exception, we didn't get the lock at all, then we don't need to unlock
        try {
            r.run();
        } finally {
            lock.unlock();
        }
    }
}
