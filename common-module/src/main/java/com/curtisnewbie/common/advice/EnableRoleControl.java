package com.curtisnewbie.common.advice;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enable Role Control
 *
 * @author yongj.zhuang
 */
@Documented
@Import(RoleControlledAdvice.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableRoleControl {

}
