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
     * Compensate action
     */
    ChainableAction<T, V> compensate(CompensateAction<T, V> compensation);

    /**
     * Provide next ChainableAction, and return the next ChainableAction
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
