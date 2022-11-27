package com.curtisnewbie.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        if (String.class.isAssignableFrom(clz)) return clz.cast(json);
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
     * Get the internally cached jsonMapper, JsonMapper is not thread-safe, so don't do any extra reconfiguration after
     * it's used
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

    /**
     * Construct a new Json Mapper with serializer/deserializer for epoch-base LocalDateTime conversion
     */
    public static JsonMapper constructsEpochJsonMapper() {
        JsonMapper jm = new JsonMapper();
        jm.setTimeZone(TimeZone.getDefault());
        jm.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        final JavaTimeModule jtm = new JavaTimeModule();
        jtm.addSerializer(LocalDateTime.class, LocalDateTimeEpochSerializer.INSTANCE);
        jtm.addDeserializer(LocalDateTime.class, LocalDateTimeEpochDeserializer.INSTANCE);
        jm.registerModule(jtm);
        return jm;
    }

    public static class LocalDateTimeEpochSerializer extends JsonSerializer<LocalDateTime> {

        public static final LocalDateTimeEpochSerializer INSTANCE = new LocalDateTimeEpochSerializer();

        @Override
        public void serialize(LocalDateTime ldt, JsonGenerator gen, SerializerProvider provider) throws IOException {
            if (ldt == null) gen.writeNull();
            gen.writeNumber(DateUtils.getEpochTime(ldt));
        }
    }

    public static class LocalDateTimeEpochDeserializer extends JsonDeserializer<LocalDateTime> {

        public static final LocalDateTimeEpochDeserializer INSTANCE = new LocalDateTimeEpochDeserializer();

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String text = p.getValueAsString();
            if (text == null) return null;

            boolean isTimestamp = true;
            for (int i = 0; i < text.length(); i++) {
                if (!Character.isDigit(text.charAt(i))) {
                    isTimestamp = false;
                    break;
                }
            }

            if (isTimestamp) {
                return LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(text)), TimeZone.getDefault().toZoneId());
            }
            return LocalDateTime.parse(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME); // fallback to ISO format
        }
    }

}
