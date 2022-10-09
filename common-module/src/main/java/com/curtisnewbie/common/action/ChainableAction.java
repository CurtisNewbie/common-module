package com.curtisnewbie.common.action;


/**
 * Action that can be chained
 *
 * @author yongj.zhuang
 */
public interface ChainableAction<T, V> {

    /**
     * Start first action in current chain
     */
    void startFirst();

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
     * Build the first ChainableAction
     * <p>
     * The first ChainableAction doesn't take any argument, the T type will be simply Void.
     * This is useful when we want to do the following:
     *
     * <pre>
     * {@code
     *      ChainableAction.buildFirst((t) -> {
     *          // ...
     *      }).then((t) -> {
     *          // ...
     *      }).startFirst();
     * }
     * </pre>
     */
    static <V> ChainableAction<Void, V> buildFirst(TFunction<Void, V> firstAction) {
        return LinkedChainableAction.ofAction(firstAction);
    }

    /**
     * Get first ChainableAction
     */
    ChainableAction<Void, ?> getFirst();

}
