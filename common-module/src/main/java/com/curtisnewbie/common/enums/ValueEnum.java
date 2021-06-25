package com.curtisnewbie.common.enums;

/**
 * An Enum with a value associated with
 * <br>
 * For example,
 * <pre>
 *     {@code
 *      public enum UserRole implements ValueEnum<UserRole, String> {
 *          ...
 *      }
 *      }
 * </pre>
 *
 * @param <T> The Enum type that implements this interface
 * @param <V> The value type of this enum
 * @author yongjie.zhuang
 */
public interface ValueEnum<T extends Enum<T> & ValueEnum<T, V>, V> {

    /**
     * Get int value
     */
    V getValue();
}
