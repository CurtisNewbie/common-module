package com.curtisnewbie.common.util;

import lombok.*;
import org.springframework.lang.*;
import org.springframework.util.*;

import java.lang.reflect.*;
import java.util.*;

/**
 * Utils that apply diff between two objects
 * <p>
 * For example:
 * </p>
 * <pre>
 * {@code
 * Dummy origin = new Dummy();
 * origin.setAge(13);
 * origin.setName("fake dummy");
 * origin.setType(Type.FAKE);
 *
 * Dummy updated = new Dummy();
 * updated.setType(Type.REAL);
 * updated.setName("real dummy");
 *
 * ObjectDiff.from(updated).applyDiffTo(origin);
 * }
 * </pre>
 *
 * @author yongj.zhuang
 */
public class ObjectDiff<T> {

    private final boolean compareNull;
    @Nullable
    private final T from;

    /**
     * fieldName -> { Field, afterValue }
     */
    @Nullable
    private Map<String, FieldAndValue> nameToFieldAndValue;

    private ObjectDiff(@Nullable final T from, final boolean compareNull) {
        this.from = from;
        this.compareNull = compareNull;
    }

    public static <T> ObjectDiff<T> from(@Nullable final T from) {
        return new ObjectDiff<>(from, false);
    }

    public static <T> ObjectDiff<T> from(@Nullable final T from, final boolean compareNull) {
        return new ObjectDiff<>(from, compareNull);
    }

    /**
     * Compare to object {@code to}
     */
    public void diff(@Nullable final T to) {
        if (from == null || to == null) return;

        // always re-instantiate the map, since we don't know which v it is
        nameToFieldAndValue = new HashMap<>();

        final Field[] fields = from.getClass().getDeclaredFields();
        for (final Field f : fields) {
            diffNonNullField(f, from, to, nameToFieldAndValue, compareNull);
        }
    }

    /**
     * Copy fields with non-null values to {@code to}, inherited fields are not included
     */
    public AppliedDiff applyDiffTo(@Nullable final T to) {
        if (from == null || to == null)
            return AppliedDiff.none();

        // always compare before we apply any diff
        diff(to);

        AppliedDiff appliedDiff = new AppliedDiff();

        // apply fromValue to the field
        for (Map.Entry<String, FieldAndValue> entry : nameToFieldAndValue.entrySet()) {
            final Field f = entry.getValue().getField();
            setFieldValue(f, to, entry.getValue().getFromValue());
            appliedDiff.markAsChanged(f.getName());
        }
        return appliedDiff;
    }

    /**
     * Check whether certain field is different, call {@link #diff(Object)} at least once before calling this method, or else we have nothing to compare with
     */
    public Diff checkFieldDiff(final String fieldName) {
        Assert.notNull(nameToFieldAndValue, "Only when #diff method is applied, then #checkFieldDiff method can be called");
        if (!nameToFieldAndValue.containsKey(fieldName))
            return Diff.none();

        final FieldAndValue fieldAndValue = nameToFieldAndValue.get(fieldName);
        return new Diff(true, fieldAndValue.getFromValue(), fieldAndValue.getToValue());
    }

    // ---------------------------- private helper method ---------------------

    /**
     * set value to object's field
     */
    private static <T> void setFieldValue(final Field f, final T object, final Object value) {
        try {
            f.set(object, value);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(String.format("Unable to copy field: %s", f), e);
        }
    }

    /**
     * Compare field between two objects ({@code from} and {@code to}), if a diff is found, it's then recorded in {@code nameToFieldAndValue}
     */
    private static <T> void diffNonNullField(final Field f, final T from, final T to, final Map<String, FieldAndValue> nameToFieldAndValue,
                                             final boolean compareNull) {

        if (Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers()))
            return;

        f.setAccessible(true);

        try {
            final Object fromValue = f.get(from);
            final Object toValue = f.get(to);

            // ignore field with null value
            if (!compareNull && (fromValue == null || toValue == null))
                return;

            // found diff
            if (!Objects.equals(fromValue, toValue)) {
                nameToFieldAndValue.computeIfAbsent(f.getName(), fn -> new FieldAndValue(f, fromValue, toValue));
            }
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(String.format("Unable to diff field: %s", f), e);
        }
    }

    @Data
    private static class FieldAndValue {
        private final Field field;
        private final Object fromValue;
        private final Object toValue;
    }

    @Data
    public static class Diff {
        private final boolean isDifferent;
        private final Object fromValue;
        private final Object toValue;

        public static Diff none() {
            return new Diff(false, null, null);
        }
    }

    @Data
    public static class AppliedDiff {

        private Set<String> fieldNames = new HashSet<>();

        /** Record which field is changed */
        private void markAsChanged(String field) {
            fieldNames.add(field);
        }

        /** Check whether the field is changed */
        public boolean isChanged(String field) {
            return fieldNames.contains(field);
        }

        public boolean hasAnyChange() {
            return !fieldNames.isEmpty();
        }

        public static AppliedDiff none() {
            return new AppliedDiff();
        }

    }

}
