package com.curtisnewbie.common.action;


/**
 * Action that can be chained
 *
 * @author yongj.zhuang
 */
public interface ChainableAction<T, V> {

    /**
     * Start the ChainableAction
     */
    void startAction(T t);

    /**
     * Compensate current action on exception
     */
    ChainableAction<T, V> compensate(CompensateAction<T, V> compensation);

    /**
     * Chain to next action, and return next ChainableAction
     */
    <R> ChainableAction<V, R> then(TFunction<V, R> next);

    /**
     * Build ChainableAction by providing the first action
     */
    static <T, V> ChainableAction<T, V> buildFirst(TFunction<T, V> firstAction) {
        return LinkedChainableAction.ofAction(firstAction);
    }

    /**
     * Get first ChainableAction
     */
    ChainableAction getFirst();

}
