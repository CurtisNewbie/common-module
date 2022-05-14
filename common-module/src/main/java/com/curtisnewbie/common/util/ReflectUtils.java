package com.curtisnewbie.common.util;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;

/**
 * Reflection Utils
 *
 * @author yongj.zhuang
 */
public final class ReflectUtils {

    private ReflectUtils() {

    }

    /** Get declared fields as stream */
    public static Stream<Field> declaredFieldsAsStream(Class<?> clz) {
        return Arrays.stream(clz.getDeclaredFields());
    }

    /** Get declared annotations on class */
    public static <T extends Annotation> Optional<T[]> annotationsOnClass(Class<?> clazz, Class<T> annotation) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(annotation);
        return Optional.of(clazz.getDeclaredAnnotationsByType(annotation));
    }

    /** Get declared annotation on class */
    public static <T extends Annotation> Optional<T> annotationOnClass(Class<?> clazz, Class<T> annotationType) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(annotationType);
        return Optional.ofNullable(clazz.getDeclaredAnnotation(annotationType));
    }

    /** Get value from field */
    public static Object getFieldValue(Field f, Object o) {
        f.setAccessible(true);
        try {
            return f.get(o);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to get value, fieldName: " + f.getName(), e);
        }
    }

    /** Get method from object's class by name */
    public static Optional<Method> findMethod(String methodName, Object o) {
        return findMethod(methodName, o.getClass());
    }

    /** Get method from class by name */
    public static Optional<Method> findMethod(String methodName, Class<?> clazz) {
        final Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method m : declaredMethods) {
            if (m.getName().equals(methodName)) {
                return Optional.of(m);
            }
        }
        return Optional.empty();
    }

    /** Invoke method */
    public static Object invokeMethod(Method method, Object obj) {
        try {
            return method.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Failed to invoke method : " + method, e);
        }
    }

    /** Get declared methods as Stream */
    public static Stream<Field> declaredMethodsAsStream(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields());
    }

    /** Get declared methods as Stream */
    public static Stream<Field> declaredMethodsAsStream(Object obj) {
        return declaredMethodsAsStream(obj.getClass());
    }

    /** Get declared annotations on field by type */
    public static <T extends Annotation> Stream<T> declaredAnnotations(Field f, Class<T> type) {
        return Arrays.stream(f.getDeclaredAnnotationsByType(type));
    }

    /** Get first declared annotations on field by type */
    public static <T extends Annotation> Optional<T> firstDeclaredAnnotation(Field f, Class<T> type) {
        return declaredAnnotations(f, type).findFirst();
    }

}
