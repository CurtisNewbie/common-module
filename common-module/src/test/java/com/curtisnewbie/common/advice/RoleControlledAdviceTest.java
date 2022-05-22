package com.curtisnewbie.common.advice;

import com.curtisnewbie.common.exceptions.UnrecoverableException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

/**
 * @author yongj.zhuang
 */
@EnableRoleControl
@SpringBootApplication(scanBasePackages = "com.curtisnewbie.common.advice")
@SpringBootTest(classes = RoleControlledAdviceTest.class)
public class RoleControlledAdviceTest {

    @Autowired
    private Dummy dummy;

    @Test
    public void should_enforce_role_control() {
        // TraceUtils doesn't work here, debugging only
        Assertions.assertThrows(UnrecoverableException.class, () -> dummy.doSomething());
    }

    @Slf4j
    @RoleControlled(rolesRequired = "admin,guest", rolesForbidden = "guest")
    @Component
    public static class Dummy {

        public void doSomething() {
            log.info("Did something");
        }
    }

}

