package com.curtisnewbie.common.util;


import com.curtisnewbie.common.exceptions.MsgEmbeddedException;

import java.util.Objects;

/**
 * Validate Utility class,
 * <p>
 * For condition that does not match the rule, a {@code MsgEmbeddedException} is thrown
 * </p>
 *
 * @author yongjie.zhuang
 */
public final class ValidUtils {

    private ValidUtils() {

    }

    public static <T> void requireNonNull(T t) throws MsgEmbeddedException {
        if (t == null) {
            throw new MsgEmbeddedException("Please enter required content");
        }
    }

    public static <T> void requireNonNull(T t, String errMsg) throws MsgEmbeddedException {
        if (t == null) {
            throw new MsgEmbeddedException(errMsg);
        }
    }

    public static <T, V> void requireEquals(T t, V v, String errMsg) throws MsgEmbeddedException {
        if (!Objects.equals(t, v)) {
            throw new MsgEmbeddedException(errMsg);
        }
    }
}
