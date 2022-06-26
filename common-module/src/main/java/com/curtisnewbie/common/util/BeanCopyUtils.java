package com.curtisnewbie.common.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.curtisnewbie.common.util.ExceptionUtils.*;

/**
 * Utilities to copy bean's properties
 *
 * @author yongjie.zhuang
 */
public final class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    /**
     * Copy properties, and convert to the given type
     *
     * @param source     source object, it should be classic POJO(e.g., it shouldn't be an instance of {@link Collection})
     * @param targetType targetType
     * @param <T>        target's generic type
     * @param <V>        source's generic type
     * @return targetObject (that is created using default constructor)
     */
    public static <T, V> V toType(T source, Class<V> targetType) {
        Objects.requireNonNull(targetType);
        if (source == null) {
            return null;
        }
        try {
            final V v = targetType.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, v);
            return v;
        } catch (NoSuchMethodException e) {
            throw illegalState(e, "Failed to convert bean to type: %s, default constructor is not found", targetType);
        } catch (ReflectiveOperationException e) {
            throw illegalState(e, "Failed to convert bean from %s to type: %s", source.getClass(), targetType);
        }
    }

    /**
     * Copy properties, and convert to the given type
     *
     * @param srcList    source object list
     * @param targetType targetType
     * @param <T>        target's generic type
     * @param <V>        source's generic type
     * @return targetObject list (wherein each object is created using default constructor)
     */
    public static <T, V> List<V> toTypeList(List<T> srcList, Class<V> targetType) {
        Objects.requireNonNull(targetType);
        if (srcList == null) {
            return new ArrayList<>();
        }
        return srcList.stream().map(t -> {
            return toType(t, targetType);
        }).collect(Collectors.toList());
    }

    /**
     * Map list of T to list of V using the given convert Function
     *
     * @param ts        list of T
     * @param converter function that converts T to V
     * @param <T>       source type
     * @param <V>       target type
     */
    public static <T, V> List<V> mapTo(List<T> ts, Function<T, V> converter) {
        return ts.stream().map(converter::apply).collect(Collectors.toList());
    }

}
