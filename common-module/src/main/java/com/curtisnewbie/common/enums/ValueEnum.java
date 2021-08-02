package com.curtisnewbie.common.enums;

/**
 * An Enum with a value, this is used together with {@link com.curtisnewbie.common.util.EnumUtils}
 * <br>
 * For example,
 * <pre>
 *     {@code
 *      public enum UserRole implements ValueEnum<String> {
 *          ...
 *      }
 *      }
 * </pre>
 *
 * @param <ValueType> The value type of this enum
 * @author yongjie.zhuang
 */
public interface ValueEnum<ValueType> {

    /**
     * Get int value
     */
    ValueType getValue();
}
