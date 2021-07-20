package com.curtisnewbie.common.enums;

/**
 * An Enum with a value, this is used together with {@link com.curtisnewbie.common.util.EnumUtils}
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
 * @param <EnumType> The Enum type that implements this interface
 * @param <ValueType> The value type of this enum
 * @author yongjie.zhuang
 */
public interface ValueEnum<EnumType extends Enum<EnumType> & ValueEnum<EnumType, ValueType>, ValueType> {

    /**
     * Get int value
     */
    ValueType getValue();
}
