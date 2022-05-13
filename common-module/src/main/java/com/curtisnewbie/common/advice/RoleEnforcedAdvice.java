package com.curtisnewbie.common.advice;

import com.curtisnewbie.common.trace.TUser;
import com.curtisnewbie.common.trace.TraceUtils;
import com.curtisnewbie.common.util.AssertUtils;
import com.curtisnewbie.common.util.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Advice that validate role before calling the annotated methods
 *
 * @author yongj.zhuang
 */
@Slf4j
@Aspect
public class RoleEnforcedAdvice {

    @PostConstruct
    public void postConstruct() {
        log.info("RoleEnforcedAdvice initialized, will enforce role control for annotated methods");
    }

    @Around("@within(roleRequired) || @annotation(roleRequired)")
    public Object before(ProceedingJoinPoint pjp, RoleRequired roleRequired) throws Throwable {

        // annotated on the class
        if (roleRequired == null) {
            roleRequired = pjp.getTarget().getClass().getDeclaredAnnotation(RoleRequired.class);
        }

        // validate role before proceed
        final String required = roleRequired.role();

        if (StringUtils.hasText(required)) {

            final TUser tUser = TraceUtils.tUser();

            // there can be multiple roles, try to split it first
            final Set<String> requiredRoles = Arrays.stream(required.split(","))
                    .map(String::trim)
                    .collect(Collectors.toSet());

            AssertUtils.isTrue(requiredRoles.contains(tUser.getRole()), "Not permitted");
        }

        return pjp.proceed();
    }

}
