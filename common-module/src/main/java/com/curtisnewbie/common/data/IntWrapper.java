package com.curtisnewbie.common.data;

import lombok.*;

/**
 * Wrapper of int value (useful for lambda)
 *
 * @author yongj.zhuang
 */
@Data
public class IntWrapper {

    /** value */
    private int value;

    public IntWrapper(int value) {
        this.value = value;
    }

    /**
     * Increment and get
     */
    public int incr() {
        return ++value;
    }

    /**
     * Decrement and get
     */
    public int decr() {
        return --value;
    }

    /**
     * Increment by
     */
    public int incrBy(int delta) {
        value += delta;
        return value;
    }

    /**
     * Decrement by
     */
    public int decrBy(int delta) {
        value -= delta;
        return value;
    }

}
