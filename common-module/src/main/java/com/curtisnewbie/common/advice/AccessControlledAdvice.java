package com.curtisnewbie.common.advice;

import com.curtisnewbie.common.exceptions.UnrecoverableException;
import com.curtisnewbie.common.trace.TUser;
import com.curtisnewbie.common.trace.TraceUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Advice that validate resource before calling the annotated methods
 *
 * @author yongj.zhuang
 */
@Slf4j
@Aspect
public class AccessControlledAdvice {

    private static final String NOT_PERMITTED = "Not permitted";

    @Autowired
    private List<RoleResourceValidator> roleResourceValidators;

    @PostConstruct
    public void postConstruct() {
        log.info("AccessControlledAdvice initialized, will enforce access control for annotated methods");
        roleResourceValidators.sort(new RoleResourceValidator.LocalBeanPreferredComparator());
        log.info("AccessControlledAdvice, sorted RoleResourceValidator: {}", roleResourceValidators);
    }

    @Around("@within(accessControlled) || @annotation(accessControlled)")
    public Object before(ProceedingJoinPoint pjp, AccessControlled accessControlled) throws Throwable {

        // annotated on the class
        if (accessControlled == null) {
            accessControlled = pjp.getTarget().getClass().getDeclaredAnnotation(AccessControlled.class);
        }

        final TUser user = TraceUtils.tUser();
        final String role = user.getRole();
        final String username = user.getUsername();

        if (!StringUtils.hasText(role)) {
            log.warn("Operation '{}' not permitted, user's role '{}' is empty, username: {}", pjp.getSignature(), role,
                    username);
            throw new UnrecoverableException("Not permitted");
        }

        final String[] resources = accessControlled.resources();
        if (resources == null || resources.length < 1) return pjp.proceed();

        if (!validateRoleResource(role, resources)) {
            log.warn("Operation '{}' not permitted, resources '{}' are required, username: '{}', role: '{}'", pjp.getSignature(),
                    resources, username, role);
            throw new UnrecoverableException("Not permitted");
        }

        return pjp.proceed();
    }

    private boolean validateRoleResource(String role, String[] resources) {
        return roleResourceValidators.get(0).validateRoleResource(role, Arrays.asList(resources));
    }

}
