package com.curtisnewbie.common.util;

import java.util.*;
import java.util.function.*;
import java.util.function.Function;
import java.util.stream.*;

/**
 * Data Structure Utils
 *
 * @author yongj.zhuang
 */
public final class StructUtils {

    private StructUtils() {
    }

    /** Map elements collection and filter duplicates */
    public static <T, V> List<V> mapDistinct(Collection<T> collection, Function<T, V> mapping) {
        return collection.stream()
                .map(mapping::apply)
                .distinct()
                .collect(Collectors.toList());
    }

    /** Find any that matches the predicate */
    public static <T> Optional<T> any(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().filter(predicate::test).findAny();
    }

    /** Find any (nullable) that matches the predicate */
    public static <T> T anyNullable(Collection<T> collection, Predicate<T> predicate) {
        return any(collection, predicate).orElse(null);
    }

    /** Find first that matches the predicate */
    public static <T> Optional<T> first(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().filter(predicate::test).findFirst();
    }

    /** Find first (nullable) that matches the predicate */
    public static <T> T firstNullable(Collection<T> collection, Predicate<T> predicate) {
        return first(collection, predicate).orElse(null);
    }

    /** Convert list of elements to map */
    public static <K, V> Map<K, V> toMap(List<V> collection, Function<V, K> groupingBy) {
        Map<K, V> m = new HashMap<>();
        collection.forEach(v -> m.put(groupingBy.apply(v), v));
        return m;
    }
}
