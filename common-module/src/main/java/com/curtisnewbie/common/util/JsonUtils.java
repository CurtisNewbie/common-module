package com.curtisnewbie.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.*;
import org.apache.poi.ss.formula.functions.*;

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
     * Write value as a prettified json string using internally cached {@link JsonMapper}
     * <p>
     * Throw RuntimeException on any {@link JsonProcessingException}
     */
    public static String uwritePretty(Object o) {
        return ExceptionUtils.throwIfError(() -> writePretty(o));
    }

    /**
     * Write value as a prettified json string using internally cached {@link JsonMapper}
     */
    public static String writePretty(Object o) throws JsonProcessingException {
        return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
    }

    /**
     * Write value as String using internally cached {@link JsonMapper}
     * <p>
     * Throw RuntimeException on any {@link JsonProcessingException}
     */
    public static String uwriteValueAsString(Object o) {
        return ExceptionUtils.throwIfError(() -> writeValueAsString(o));
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
     * Read value as object using internally cached {@link JsonMapper}
     * <p>
     * Throw RuntimeException on any {@link JsonProcessingException}
     */
    public static <T> T ureadValueAsObject(String json, Class<T> clz) {
        return ExceptionUtils.throwIfError(() -> readValueAsObject(json, clz));
    }

    /**
     * Read value as object using internally cached {@link JsonMapper}
     */
    public static <T> T readValueAsObject(String json, TypeReference<T> typeReference) throws JsonProcessingException {
        return jsonMapper.readValue(json, typeReference);
    }

    /**
     * Read value as object using internally cached {@link JsonMapper}
     * <p>
     * Throw RuntimeException on any {@link JsonProcessingException}
     */
    public static <T> T ureadValueAsObject(String json, TypeReference<T> typeReference) {
        return ExceptionUtils.throwIfError(() -> readValueAsObject(json, typeReference));
    }

    /**
     * Read value as List using internally cached {@link JsonMapper}
     */
    public static <T> List<T> readValueAsList(String json, Class<T> clz) throws JsonProcessingException {
        return jsonMapper.readValue(json, new TypeReference<List<T>>() {
        });
    }

    /**
     * Read value as List using internally cached {@link JsonMapper}
     * <p>
     * Throw RuntimeException on any {@link JsonProcessingException}
     */
    public static <T> List<T> ureadValueAsList(String json, Class<T> clz) {
        return ExceptionUtils.throwIfError(() -> readValueAsList(json, clz));
    }

    /**
     * Get the internally cached jsonMapper, JsonMapper is not thread-safe, so don't do any extra reconfiguration after it's used
     */
    public static JsonMapper getJsonMapper() {
        return jsonMapper;
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
        jm.registerModule(new JavaTimeModule());
        return jm;
    }

}
