package com.curtisnewbie.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.*;

/**
 * Utils for JSON processing
 *
 * @author yongjie.zhuang
 */
public final class JsonUtils {

    private static final JsonMapper jsonMapper = constructsJsonMapper();

    private JsonUtils() {

    }

    /**
     * Write value as String using internally cached {@link JsonMapper}
     */
    public static String writeValueAsString(Object o) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(o);
    }

    /**
     * Read value as object using internally cached {@link JsonMapper}
     */
    public static <T> T readValueAsObject(String json, Class<T> clz) throws JsonProcessingException {
        return jsonMapper.readValue(json, clz);
    }

    /**
     * Read value as List using internally cached {@link JsonMapper}
     */
    public static <T> List<T> readValueAsList(String json, Class<T> clz) throws JsonProcessingException {
        return jsonMapper.readValue(json, new TypeReference<List<T>>() {
        });
    }

    /**
     * Construct JavaType
     */
    public static JavaType constructType(Class<?> clz) {
        return jsonMapper.constructType(clz);
    }

    /**
     * Construct a new Json Mapper
     */
    public static JsonMapper constructsJsonMapper() {
        JsonMapper jm = new JsonMapper();
        jm.setTimeZone(TimeZone.getDefault());
        jm.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return jm;
    }

}
