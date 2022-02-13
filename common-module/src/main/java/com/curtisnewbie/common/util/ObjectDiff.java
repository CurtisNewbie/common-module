package com.curtisnewbie.common.util;

import org.springframework.lang.*;

import java.lang.reflect.*;

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

    private final T t;

    private ObjectDiff(@Nullable final T t) {
        this.t = t;
    }

    public static <T> ObjectDiff<T> from(@Nullable final T t) {
        return new ObjectDiff<>(t);
    }

    /**
     * Copy fields with non-null values to v, inherited fields are not included
     */
    public void applyDiffTo(@Nullable final T v) {
        if (t == null || v == null) return;

        final Field[] fields = t.getClass().getDeclaredFields();
        for (final Field f : fields) {
            copyTo(f, t, v);
        }
    }

    // ---------------------------- private helper method ---------------------

    /**
     * Copy value of field {@code f} from object '{@code from}' to object '{@code to}'
     */
    private static <T> void copyTo(final Field f, final T from, final T to) {
        if (Modifier.isFinal(f.getModifiers()) || Modifier.isStatic(f.getModifiers()))
            return;

        f.setAccessible(true);

        try {
            final Object o = f.get(from);

            // ignore field with null value
            if (o == null) return;

            f.set(to, o);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(String.format("Unable to copy field: %s", f), e);
        }
    }

}
