package com.curtisnewbie.common.util;


import com.curtisnewbie.common.exceptions.MsgEmbeddedException;
import org.springframework.util.StringUtils;

import java.util.Collection;
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
    private static final String DEFAULT_NON_NULL_MSG = "Required parameters cannot be null or empty";

    private ValidUtils() {

    }

    public static <T> void requireNotEmpty(T[] c, String msg) throws MsgEmbeddedException {
        requireNonNull(c);
        if (c.length == 0)
            throw new MsgEmbeddedException(msg);
    }

    public static <T> void requireNotEmpty(T[] c) throws MsgEmbeddedException {
        requireNonNull(c);
        if (c.length == 0)
            throw new MsgEmbeddedException();
        requireNotEmpty(c, DEFAULT_NON_NULL_MSG);
    }

    public static void requireNotEmpty(Collection<?> c) throws MsgEmbeddedException {
        requireNonNull(c);
        if (c.isEmpty())
            throw new MsgEmbeddedException(DEFAULT_NON_NULL_MSG);
    }

    public static void requireNotEmpty(String text) throws MsgEmbeddedException {
        requireNotEmpty(text, DEFAULT_NON_NULL_MSG);
    }

    public static void requireNotEmpty(String text, String errMsg) throws MsgEmbeddedException {
        if (!StringUtils.hasText(text)) {
            throw new MsgEmbeddedException(errMsg);
        }
    }

    public static <T> void requireNonNull(T t) throws MsgEmbeddedException {
        requireNonNull(t, DEFAULT_NON_NULL_MSG);
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

    public static <T, V> void requireNotEquals(T t, V v, String errMsg) throws MsgEmbeddedException {
        if (Objects.equals(t, v)) {
            throw new MsgEmbeddedException(errMsg);
        }
    }

    public static void assertTrue(boolean result, String errMsg) throws MsgEmbeddedException {
        if (result != true)
            throw new MsgEmbeddedException(errMsg);
    }
}
