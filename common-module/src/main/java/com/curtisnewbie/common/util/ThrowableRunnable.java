package com.curtisnewbie.common.util;

/**
 * A Runnable that can throw exception
 *
 * @author yongj.zhuang
 */
@FunctionalInterface
public interface ThrowableRunnable {

    /**
     * Run
     */
    void run() throws Exception;
}

