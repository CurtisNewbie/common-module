package com.curtisnewbie.common.action;

/**
 * Same as Function, except that you can throw exception in it
 *
 * @author yongj.zhuang
 */
@FunctionalInterface
public interface TFunction<T, R> {

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t) throws Exception;

}
