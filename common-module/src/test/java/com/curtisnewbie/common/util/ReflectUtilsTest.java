package com.curtisnewbie.common.util;

import com.curtisnewbie.common.advice.RoleRequired;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.management.relation.Role;
import java.util.Optional;

/**
 * @author yongj.zhuang
 */
@Slf4j
@RoleRequired(role = "random")
public class ReflectUtilsTest {

    @Test
    public void should_parse_class_annotation() {
        final Optional<RoleRequired> slf4j = ReflectUtils.annotationOnClass(ReflectUtilsTest.class, RoleRequired.class);
        Assertions.assertTrue(slf4j.isPresent());
    }
}
