package com.curtisnewbie.common.preconf;

import com.fasterxml.jackson.databind.*;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.*;
import org.springframework.web.client.*;

import java.util.*;

/**
 * Import this bean to have a preconfigured RestTemplate
 *
 * @author yongj.zhuang
 */
public class RestTemplatePreConfigured {

    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate(MappingJackson2HttpMessageConverter converter) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().replaceAll(c -> c instanceof MappingJackson2HttpMessageConverter ? converter : c);
        return restTemplate;
    }

    @ConditionalOnMissingBean(MappingJackson2HttpMessageConverter.class)
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper); // make sure we use a preconfigured object mapper
        return converter;
    }
}
