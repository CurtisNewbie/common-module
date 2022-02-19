package com.curtisnewbie.common.util;

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

}
