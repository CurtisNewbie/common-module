package com.curtisnewbie.common.util;

/**
 * Utils for string sanitization
 *
 * @author yongj.zhuang
 */
public final class SanitizeUtils {

    private SanitizeUtils() {

    }

    /**
     * Replace all   or '　' with normal spaces
     */
    public static String replaceSpecialSpace(String src) {
        if (src == null) return null;
        return src.replaceAll("[\u00a0　]", " ");
    }

    /** Replace all  , '　' and normal spaces */
    public static String removeSpaces(String src) {
        if (src == null) return null;
        return src.replaceAll("[\u00a0　 ]", "");
    }
}
