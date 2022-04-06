package com.curtisnewbie.common.data;

/**
 * Container that contains three values
 *
 * @author yongj.zhuang
 */
public final class TriContainer<T, K, V> {

    /** Left value */
    private final T left;

    /** Mid value */
    private final K mid;

    /** Right value */
    private final V right;

    public TriContainer(T left, K mid, V right) {
        this.left = left;
        this.mid = mid;
        this.right = right;
    }

    public T getLeft() {
        return left;
    }

    public K getMid() {
        return mid;
    }

    public V getRight() {
        return right;
    }
}
