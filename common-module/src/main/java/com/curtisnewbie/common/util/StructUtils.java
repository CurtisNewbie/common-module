package com.curtisnewbie.common.util;

import com.curtisnewbie.common.data.*;

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

    /**
     * Convert list of elements to map
     *
     * @param collection collection (of V)
     * @param groupingBy key mapping function (V -> key K)
     * @return map of K and V
     */
    public static <K, V> Map<K, V> toMap(List<V> collection, Function<V, K> groupingBy) {
        return toMap(collection, groupingBy, Function.identity());
    }

    /**
     * Convert list of elements to map
     *
     * @param collection collection (of T)
     * @param toKey      key mapping function (T -> key K)
     * @param toValue    value mapping function (T -> value V)
     * @return map of K and V
     */
    public static <K, V, T> Map<K, V> toMap(List<T> collection, Function<T, K> toKey, Function<T, V> toValue) {
        return collection.stream()
                .collect(Collectors.toMap(toKey, toValue));
    }

    /**
     * Convert stream of {@link BiContainer} to map, the left value becomes the key, and right value becomes the value
     */
    public static <K, V> Map<K, V> toMap(Stream<BiContainer<K, V>> s) {
        return s.collect(Collectors.toMap(BiContainer::getLeft, BiContainer::getRight));
    }

    /** Build new HashSet */
    public static <T> Set<T> newHashSet(T... targs) {
        Set<T> s = new HashSet<>();
        for (T t : targs) {
            s.add(t);
        }
        return s;
    }

    /** Get first value from set */
    public static <T> T first(Set<T> s) {
        for (T t : s) {
            return t;
        }
        return null;
    }

}
