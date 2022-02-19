package com.curtisnewbie.common.util;

import org.springframework.util.*;

import java.util.*;
import java.util.function.*;

/**
 * Utils for working with {@link Optional}
 *
 * @author yongj.zhuang
 */
public final class OptionalUtils {

    private OptionalUtils() {
    }

    /**
     * Get Value from Optional
     *
     * @param opt      optional
     * @param onAbsent called to supply value if Optional is absent
     * @param <T>      value being returned
     */
    public static <T> T get(final Optional<T> opt, final Supplier<T> onAbsent) {
        Assert.notNull(opt, "optional == null");
        Assert.notNull(onAbsent, "onAbsent == null");

        if (opt.isPresent())
            return opt.get();
        else
            return onAbsent.get();
    }

    /**
     * Consume value from Optional, convert and return it
     *
     * @param opt          optional
     * @param converter    converter
     * @param defaultValue default value
     */
    public static <T, V> V getAndConvert(final Optional<T> opt, final Function<T, V> converter, final V defaultValue) {
        Assert.notNull(opt, "optional == null");
        Assert.notNull(converter, "converter == null");
        Assert.notNull(defaultValue, "onAbsent == null");

        if (opt.isPresent())
            return converter.apply(opt.get());
        else
            return defaultValue;
    }

    /**
     * Get nullable value, convert and return it, if the value is null then the default value is returned instead
     *
     * @param nullableValue nullable value
     * @param converter     converter
     * @param defaultValue  default value
     */
    public static <T, V> V getNullableAndConvert(final T nullableValue, final Function<T, V> converter, final V defaultValue) {
        return getAndConvert(Optional.ofNullable(nullableValue), converter, defaultValue);
    }

    /**
     * Get nullable value, a default value is returned if the value is actually null
     *
     * @param nullableValue nullable value
     * @param defaultValue  default value
     */
    public static <T> T getNullable(final T nullableValue, final T defaultValue) {
        return Optional.ofNullable(nullableValue).orElse(defaultValue);
    }

}
