package com.curtisnewbie.common.util;

/**
 * @author yongj.zhuang
 */
public final class StrUtils {

    private StrUtils() {
    }

    public static String shrink(String s, int maxLen) {
        if (s == null || maxLen < 0) return s;
        if (s.length() <= maxLen) return s;
        return s.substring(0, maxLen);
    }
}
