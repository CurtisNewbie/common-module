package com.curtisnewbie.common.action;

import lombok.*;

/**
 * Compensation
 *
 * @author yongj.zhuang
 */
@Data
public class Compensation<T> {

    private final boolean continued;
    private final T fallback;

    public Compensation(boolean continued, T fallback) {
        this.continued = continued;
        this.fallback = fallback;
    }
}
