package com.curtisnewbie.common.util;

import com.curtisnewbie.common.enums.IntEnum;
import com.curtisnewbie.common.enums.ValueEnum;

import java.util.Objects;

/**
 * @author yongjie.zhuang
 */
public final class EnumUtils {

    private EnumUtils() {

    }

    /**
     * Get the corresponding enum by the given int value {@code v}
     *
     * @param v     value
     * @param clazz class of the enum, which must also be an IntEnum
     * @param <T>   class of the enum
     * @return enum
     */
    public static <T extends Enum<T> & IntEnum<T>> T parse(int v, Class<T> clazz) {
        T[] enums = clazz.getEnumConstants();
        if (enums == null)
            return null;
        for (T t : enums) {
            if (t.getValue() == v) {
                return t;
            }
        }
        return null;
    }

    /**
     * Get the corresponding enum by the given value
     *
     * @param value value
     * @param clazz class of the enum, which must also be an IntEnum
     * @param <T>   class of the enum
     * @param <V>   type of the value in enum
     * @return enum
     */
    public static <T extends Enum<T> & ValueEnum<V>, V> T parse(V value, Class<T> clazz) {
        T[] enums = clazz.getEnumConstants();
        if (enums == null)
            return null;
        for (T t : enums) {
            if (Objects.equals(t.getValue(), value)) {
                return t;
            }
        }
        return null;
    }
}
