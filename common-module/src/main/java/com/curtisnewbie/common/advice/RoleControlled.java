package com.curtisnewbie.common.advice;

import com.curtisnewbie.common.trace.TraceUtils;

import java.lang.annotation.*;

/**
 * Role Control for the execution of the annotation method
 * <p>
 * This relies on the {@link RoleControlledAdvice} and {@link TraceUtils#tUser()}
 *
 * @see TraceUtils#tUser()
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RoleControlled {

    /**
     * Roles that are permitted (can be multiple roles delimited by comma, e.g., 'admin,user' requires 'admin' or
     * 'user')
     */
    String rolesRequired() default "";

    /**
     * Roles that are forbidden (can be multiple roles delimited by comma, e.g., 'admin,user' forbidden 'admin' and
     * 'user')
     */
    String rolesForbidden() default "";
}
