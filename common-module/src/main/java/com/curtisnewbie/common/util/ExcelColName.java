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
public @interface ExcelColName {

    String value() default "";
}
