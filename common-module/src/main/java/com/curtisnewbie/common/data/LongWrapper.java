package com.curtisnewbie.common.data;

import lombok.Data;

/**
 * Wrapper of long value (useful for lambda)
 *
 * @author yongj.zhuang
 */
@Data
public class LongWrapper {

    /** value */
    private long value;

    public LongWrapper(long value) {
        this.value = value;
    }

    /**
     * Increment and get
     */
    public long incr() {
        return ++value;
    }

    /**
     * Decrement and get
     */
    public long decr() {
        return --value;
    }

    /**
     * Increment by
     */
    public long incrBy(long delta) {
        value += delta;
        return value;
    }

    /**
     * Decrement by
     */
    public long decrBy(long delta) {
        value -= delta;
        return value;
    }

}
