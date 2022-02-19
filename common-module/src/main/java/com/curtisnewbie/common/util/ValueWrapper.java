package com.curtisnewbie.common.util;

import lombok.*;

/**
 * Wrapper of value (useful for lambda)
 *
 * @author yongj.zhuang
 */
@Data
public class ValueWrapper<T> {

    /** value */
    private T value;

    public ValueWrapper(T t) {
        this.value = t;
    }

}
