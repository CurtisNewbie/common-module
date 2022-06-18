package com.curtisnewbie.common.domain;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Domain Object
 *
 * @author yongj.zhuang
 */
@Documented
@Component
@Scope(scopeName = DefaultListableBeanFactory.SCOPE_PROTOTYPE)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Domain {
}
