package com.curtisnewbie.common.util;

import java.util.*;

/**
 * Chained Map
 *
 * @author yongj.zhuang
 */
public class ChainedMap {

    private Map<String, String> param;

    public ChainedMap(Map<String, String> param) {
        this.param = new HashMap<>(param);
    }

    public ChainedMap() {
        this.param = new HashMap<>();
    }

    /**
     * Put value for key
     */
    public ChainedMap thenPut(boolean condition, String k, String v) {
        if (condition) thenPut(k, v);
        return this;
    }

    /**
     * Put value for key
     */
    public ChainedMap thenPut(String k, String v) {
        param.put(k, v);
        return this;
    }

    /**
     * Get the internal Map
     */
    public Map<String, String> get() {
        return param;
    }
}
