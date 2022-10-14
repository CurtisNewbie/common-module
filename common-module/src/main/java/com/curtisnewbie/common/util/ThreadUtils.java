package com.curtisnewbie.common.util;

/**
 * Utils for Threads
 *
 * @author yongj.zhuang
 */
public final class ThreadUtils {

    private ThreadUtils() {
    }

    /**
     * Try to sleep, if InterruptedException is thrown, call {@link Thread#interrupt() } to 'resume' and return
     *
     * @return wasInterrupted
     */
    public static boolean sleep(long milliSec) {
        boolean isInterrupted = false;
        try {
            Thread.sleep(milliSec);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            isInterrupted = true;
        }
        return isInterrupted;
    }
}
