package com.curtisnewbie.common.advice;

import com.curtisnewbie.common.trace.TraceUtils;

import java.lang.annotation.*;

/**
 * Resource-based access control for the execution of the annotation method
 * <p>
 * This relies on the {@link AccessControlledAdvice} and {@link TraceUtils#tUser()}
 *
 * @see TraceUtils#tUser()
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessControlled {

    /**
     * Resources required, not validated if empty
     */
    String[] resources() default {};

}
