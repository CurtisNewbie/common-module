package com.curtisnewbie.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Holder of application context
 *
 * @author yongjie.zhuang
 */
@Configuration
public class AppContextHolder implements ApplicationContextAware {

    private static final AtomicReference<ApplicationContext> appCtx = new AtomicReference<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appCtx.compareAndSet(null, applicationContext);
    }

    public static ApplicationContext getApplicationContext() {
        return appCtx.get();
    }
}
