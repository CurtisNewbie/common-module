package com.curtisnewbie.common.advice;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enable resource-based access control
 *
 * @author yongj.zhuang
 */
@Documented
@Import(AccessControlled.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableAccessControl {

}
