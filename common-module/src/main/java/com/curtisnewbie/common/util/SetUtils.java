package com.curtisnewbie.common.util;

import org.apache.poi.ss.formula.functions.*;

import java.util.*;

/**
 * Utils for Set
 *
 * @author yongj.zhuang
 */
public final class SetUtils {

    private SetUtils() {
    }

    /** Build new HashSet */
    public static Set<T> newHashSet(T... targs) {
        Set<T> s = new HashSet<>();
        for (T t : targs) {
            s.add(t);
        }
        return s;
    }
}
