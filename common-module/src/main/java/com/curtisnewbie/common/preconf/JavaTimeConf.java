package com.curtisnewbie.common.preconf;

import com.curtisnewbie.common.util.JsonUtils;
import com.fasterxml.jackson.databind.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yongj.zhuang
 */
@Configuration
public class JavaTimeConf {

    @Bean
    public Module javaTimeModule() {
        return JsonUtils.javaEpochTimeModule();
    }
}
