package com.curtisnewbie.common.trace;

import brave.baggage.BaggageField;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Trace Utils
 *
 * @author yongj.zhuang
 */
public final class TraceUtils {

    public static final String AUTH_TOKEN = "AUTH_TOKEN";

    /** Put value for the key */
    public static void put(String key, String value) {
        Assert.notNull(key, "key == null");
        BaggageField bag = BaggageField.getByName(key);
        if (bag == null) {
            bag = BaggageField.create(key);
        }
        bag.updateValue(value);
    }

    /** Get value for the key */
    @Nullable
    public static String get(String key) {
        BaggageField bag = BaggageField.getByName(key);
        return bag != null ? bag.getValue() : null;
    }
}

