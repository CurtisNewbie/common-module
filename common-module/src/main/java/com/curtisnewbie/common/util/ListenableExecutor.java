package com.curtisnewbie.common.util;

import lombok.*;
import org.springframework.util.*;

import java.util.concurrent.*;

/**
 * A listenable, wrapper of {@link Executor}
 * <p>
 * This executor supports four kinds of lifecycle callbacks (for the submitted Runnable)
 * </p>
 * <ol>
 * <li>{@link OnRunnableSubmitted} is invoked in the {@link #execute(Runnable)} method before it's truly
 * submitted to the wrapped executor, it's invoked on the caller thread</li>
 * <li>{@link OnRunnableRejected} is invoked in {@link #execute(Runnable)} method, when the submission of the Runnable
 * is rejected, it's invoked on the caller thread</li>
 * <li>{@link OnRunnableExecute} is invoked before the {@link Runnable#run()} method, when the Runnable is rejected by
 * the wrapped executor, this callback will not be invoked, since it's not considered truly submitted at all, it's
 * invoked on the allocated worker thread</li>
 * <li>{@link OnRunnableComplete} is invoked after the {@link Runnable#run()} method, it will still be invoked when exception occurs,
 * it's invoked on the allocated worker thread</li>
 * </ol>
 *
 * @author yongj.zhuang
 */
public final class ListenableExecutor {

    private final OnRunnableComplete onRunnableComplete;
    private final OnRunnableExecute onRunnableExecute;
    private final OnRunnableSubmitted onRunnableSubmitted;
    private final OnRunnableRejected onRunnableRejected;

    /** Delegating executor */
    private final Executor delegatingExecutor;

    @Builder
    public ListenableExecutor(OnRunnableComplete onRunnableComplete,
                              OnRunnableExecute onRunnableExecute,
                              OnRunnableSubmitted onRunnableSubmitted,
                              OnRunnableRejected onRunnableRejected,
                              Executor delegatingExecutor) {

        Assert.notNull(delegatingExecutor, "executor == null");

        this.onRunnableSubmitted = onRunnableSubmitted;
        this.onRunnableComplete = onRunnableComplete;
        this.onRunnableExecute = onRunnableExecute;
        this.onRunnableRejected = onRunnableRejected;
        this.delegatingExecutor = delegatingExecutor;
    }

    /** Execute Runnable */
    public void execute(Runnable r) {
        Assert.notNull(r, "Runnable == null");

        // before submitting it to the wrapped executor
        onSubmitted();

        try {
            delegatingExecutor.execute(new ListenableRunnable(r));
        } catch (Exception e) {
            // called when the runnable is rejected
            onRejected();
            throw e;
        }
    }

    private void onComplete() {
        if (onRunnableComplete != null)
            onRunnableComplete.run();
    }

    private void onSubmitted() {
        if (onRunnableSubmitted != null)
            onRunnableSubmitted.run();
    }

    private void onExecute() {
        if (onRunnableExecute != null)
            onRunnableExecute.run();
    }

    private void onRejected() {
        if (onRunnableRejected != null)
            onRunnableRejected.run();
    }

    /**
     * <p>
     * Create a counting {@link ListenableExecutor }
     * </p>
     * <p>
     * When a Runnable is submitted, it incr counter by one, if the submission is rejected,
     * the counter decr by one. For every Runnable that is completed (including those that throw exceptions),
     * the counter decr by one.
     * </p>
     */
    public static ListenableExecutor countingListenableExecutor(final ThreadSafeCounter counter, final Executor executor) {
        return ListenableExecutor.builder()
                .delegatingExecutor(executor)
                .onRunnableComplete(counter::decr) // complete -1
                .onRunnableSubmitted(counter::incr) // submit +1
                .onRunnableRejected(counter::decr) // reject -1
                .build();
    }

    private class ListenableRunnable implements Runnable {

        private final Runnable r;

        private ListenableRunnable(Runnable r) {
            this.r = r;
        }

        @Override
        public void run() {
            try {
                onExecute();
                r.run();
            } finally {
                onComplete();
            }
        }
    }

    public interface OnRunnableComplete extends Runnable {
    }

    public interface OnRunnableExecute extends Runnable {
    }

    public interface OnRunnableSubmitted extends Runnable {
    }

    public interface OnRunnableRejected extends Runnable {
    }


}
