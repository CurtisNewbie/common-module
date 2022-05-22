package com.curtisnewbie.common.dao;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enable {@link MBTraceInterceptor}
 *
 * @author yongj.zhuang
 */
@Import(MBTraceInterceptor.class)
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableMBTraceInterceptor {
}
