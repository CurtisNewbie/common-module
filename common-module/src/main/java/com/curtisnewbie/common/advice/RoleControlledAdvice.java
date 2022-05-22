package com.curtisnewbie.common.advice;

import com.curtisnewbie.common.exceptions.UnrecoverableException;
import com.curtisnewbie.common.trace.TUser;
import com.curtisnewbie.common.trace.TraceUtils;
import com.curtisnewbie.common.util.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
public class RoleControlledAdvice {

    @PostConstruct
    public void postConstruct() {
        log.info("RoleControlledAdvice initialized, will enforce role control for annotated methods");
    }

    @Around("@within(roleControlled) || @annotation(roleControlled)")
    public Object before(ProceedingJoinPoint pjp, RoleControlled roleControlled) throws Throwable {

        // annotated on the class
        if (roleControlled == null) {
            roleControlled = pjp.getTarget().getClass().getDeclaredAnnotation(RoleControlled.class);
        }

        final TUser tUser = TraceUtils.tUser();
        final String role = tUser.getRole();

        // validate role before proceed
        final String required = roleControlled.rolesRequired();
        if (StringUtils.hasText(required) && !roles(required).contains(role)) {
            log.warn("Operation '{}' not permitted, roles '{}' are required, user's role: '{}'", pjp.getSignature(), required, role);
            throw new UnrecoverableException("Not permitted");
        }

        final String forbidden = roleControlled.rolesForbidden();
        if (StringUtils.hasText(forbidden) && roles(forbidden).contains(role)) {
            log.warn("Operation '{}' not permitted, roles '{}' are forbidden, user's role: '{}'", pjp.getSignature(), forbidden, role);
            throw new UnrecoverableException("Not permitted");
        }

        return pjp.proceed();
    }

    private static Set<String> roles(String roles) {
        // there can be multiple roles, try to split it first
        return Arrays.stream(roles.split(","))
                .map(String::trim)
                .collect(Collectors.toSet());
    }

}
