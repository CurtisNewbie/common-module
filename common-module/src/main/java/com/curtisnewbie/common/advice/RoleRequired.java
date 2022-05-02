package com.curtisnewbie.common.advice;

import com.curtisnewbie.common.trace.TraceUtils;

import java.lang.annotation.*;

/**
 * Role is required for the execution of the annotation method
 * <p>
 * This relies on the {@link RoleEnforcedAdvice} and {@link TraceUtils#tUser()}
 *
 * @see TraceUtils#tUser()
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleRequired {

    /**
     * Role (can be multiple roles delimited by comma)
     */
    String role();
}
