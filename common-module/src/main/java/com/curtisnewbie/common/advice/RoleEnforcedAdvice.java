package com.curtisnewbie.common.advice;

import com.curtisnewbie.common.trace.TUser;
import com.curtisnewbie.common.trace.TraceUtils;
import com.curtisnewbie.common.util.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

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

    @Around("@annotation(roleRequired)")
    public Object before(ProceedingJoinPoint pjp, RoleRequired roleRequired) throws Throwable {

        // validate role before proceed
        final String required = roleRequired.role();
        if (StringUtils.hasText(required)) {
            TUser tUser = TraceUtils.tUser();
            AssertUtils.equals(tUser.getRole(), required, "Not permitted");
        }

        return pjp.proceed();
    }

}
