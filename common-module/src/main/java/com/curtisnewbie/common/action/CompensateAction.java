package com.curtisnewbie.common.action;

/**
 * Compensate Action
 *
 * @author yongj.zhuang
 */
@FunctionalInterface
public interface CompensateAction<T, V> {

    /**
     * Compensate the action
     */
    Compensation<V> doCompensate(Exception e, T prev);
}
