package com.curtisnewbie.common.domain;

import org.springframework.context.*;
import org.springframework.stereotype.*;

/**
 * Abstract Domain Factory
 *
 * @author yongj.zhuang
 */
@Component
public abstract class AbstractDomainFactory<T> {

    private final Class<T> domainClz;
    protected ApplicationContext applicationContext;

    public AbstractDomainFactory(Class<T> domainClz) {
        this.domainClz = domainClz;
    }

    /**
     * Create empty domain
     */
    public T empty() {
        return applicationContext.getBean(domainClz);
    }

}
