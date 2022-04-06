package com.curtisnewbie.common.data;

/**
 * Container that contains two values
 *
 * @author yongj.zhuang
 */
public final class BiContainer<T, V> {

    /** Left value */
    private final T left;

    /** Right value */
    private final V right;

    public BiContainer(T left, V right) {
        this.left = left;
        this.right = right;
    }

    public T getLeft() {
        return left;
    }

    public V getRight() {
        return right;
    }
}
