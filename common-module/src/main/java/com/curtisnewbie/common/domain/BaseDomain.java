package com.curtisnewbie.common.domain;

import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

/**
 * Base domain object
 *
 * @author yongj.zhuang
 */
@Scope(scopeName = DefaultListableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class BaseDomain {
}
