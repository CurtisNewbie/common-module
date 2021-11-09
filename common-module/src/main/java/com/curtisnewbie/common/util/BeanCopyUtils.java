package com.curtisnewbie.common.util;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
     * @param source     source object, it should be classic POJO(e.g., it shouldn't be instance of {@link Collection})
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
        // source shouldn't be object of List
        if (source instanceof Collection) {
            throw new IllegalStateException("T is a Collection, which is not supported");
        }
        V v;
        try {
            v = targetType.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to convert bean", e);
        }
        BeanUtils.copyProperties(source, v);
        return v;
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
     * Copy properties, and convert to the given type
     *
     * @param srcPageInfo source object page info
     * @param targetType  targetType
     * @param <T>         target's generic type
     * @param <V>         source's generic type
     * @return pageInfo of targetType
     */
    @Deprecated
    public static <T, V> PageInfo<V> toPageList(PageInfo<T> srcPageInfo, Class<V> targetType) {
        Objects.requireNonNull(targetType);
        if (srcPageInfo == null) {
            return new PageInfo<>();
        }
        PageInfo<V> vPage = PageInfo.of(toTypeList(srcPageInfo.getList(), targetType));
        vPage.setTotal(srcPageInfo.getTotal());
        return vPage;
    }

    /**
     * Copy properties of beans in list , convert them to the given type, and wrap returned list with a {@link
     * PageInfo}
     * <p>
     * This is a convenient method that works just like below:
     * </p>
     * <pre>
     * PageInfo<Demo> pi = PageInfo.of(demoService.getDemos(...));
     * return BeanCopyUtils.toPageList(pi, Demo.class);
     * </pre>
     *
     * @param srcList    source object list
     * @param targetType targetType
     * @param <T>        target's generic type
     * @param <V>        source's generic type
     * @return pageInfo of targetType
     */
    @Deprecated
    public static <T, V> PageInfo<V> pageInfoOf(List<T> srcList, Class<V> targetType) {
        PageInfo<T> tp = PageInfo.of(srcList);
        return toPageList(tp, targetType);
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
