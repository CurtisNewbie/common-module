package com.curtisnewbie.common.action;

import lombok.*;
import org.springframework.util.Assert;

/**
 * Implementation of ChainableAction
 *
 * @author yongj.zhuang
 */
@ToString
public class LinkedChainableAction<T, V> implements ChainableAction<T, V> {

    private final ChainableAction<?, T> prevAction;
    private final TFunction<T, V> action;

    private ChainableAction<V, ?> nextAction;
    private CompensateAction<T, V> compensateAction;

    public LinkedChainableAction(ChainableAction<?, T> prevAction, TFunction<T, V> action) {
        Assert.notNull(action, "action can't be null");
        this.prevAction = prevAction;
        this.action = action;
    }

    @Override
    public void startAction(T t) {
        boolean doNext = false;
        V v = null;
        try {
            v = this.action.apply(t);
            doNext = true;
        } catch (Exception e) {
            if (compensateAction != null) {
                final Compensation<V> compensation = compensateAction.doCompensate(e, t);
                if (compensation.isContinued() && nextAction != null) {
                    nextAction.startAction(compensation.getFallback());
                }
            } else {
                throw new IllegalStateException("Uncompensated exception caught", e);
            }
        }
        if (doNext && nextAction != null)
            nextAction.startAction(v);
    }

    @Override
    public ChainableAction<T, V> compensate(CompensateAction<T, V> compensation) {
        this.compensateAction = compensation;
        return this;
    }

    @Override
    public <R> ChainableAction<V, R> then(TFunction<V, R> next) {
        this.nextAction = new LinkedChainableAction(this, next);
        return (ChainableAction<V, R>) nextAction;
    }

    @Override
    public ChainableAction getFirst() {
        if (prevAction == null) return this;
        return prevAction.getFirst();
    }

    @Override
    public void startFirst() {
        getFirst().startAction(null);
    }

    public static <K, J> LinkedChainableAction<K, J> ofAction(TFunction<K, J> action) {
        return new LinkedChainableAction<K, J>(null, action);
    }
}
