package com.curtisnewbie.common.util;

import java.lang.annotation.*;

/**
 * An Excel field
 *
 * @author yongj.zhuang
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelCol {

    /** Name of the Excel column */
    String value() default "";

    /** Name of the method (on the value object's class) to convert value to String (it's only used for generating excel) */
    String toStringMethod() default "";
}
