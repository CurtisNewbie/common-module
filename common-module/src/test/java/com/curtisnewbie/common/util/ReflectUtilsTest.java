package com.curtisnewbie.common.util;

import com.curtisnewbie.common.advice.RoleControlled;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * @author yongj.zhuang
 */
@Slf4j
@RoleControlled(rolesRequired = "random")
public class ReflectUtilsTest {

    @Test
    public void should_parse_class_annotation() {
        final Optional<RoleControlled> slf4j = ReflectUtils.annotationOnClass(ReflectUtilsTest.class, RoleControlled.class);
        Assertions.assertTrue(slf4j.isPresent());
    }
}
