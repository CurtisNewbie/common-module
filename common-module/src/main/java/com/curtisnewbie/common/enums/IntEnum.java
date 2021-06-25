package com.curtisnewbie.common.enums;

/**
 * An Enum with an int value assigned, this is used together with {@link com.curtisnewbie.common.util.EnumUtils}
 *
 * @param <T> The Enum type that implements this IntEnum
 * @author yongjie.zhuang
 */
public interface IntEnum<T extends Enum<T>> {

    /**
     * Get int value
     */
    int getValue();
}
