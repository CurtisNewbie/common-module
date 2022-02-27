package com.curtisnewbie.common.util;

import java.lang.annotation.*;

/**
 * Name of an Excel field
 *
 * @author yongj.zhuang
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelCol {

    /** Name of the Excel column */
    String value() default "";

    /** name of the method to convert value to String */
    String toStringMethod() default "";
}
