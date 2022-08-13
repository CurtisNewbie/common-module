package com.curtisnewbie.common.util;

import org.springframework.util.Assert;

/**
 * Utils for URL
 *
 * @author yongj.zhuang
 */
public final class UrlUtils {

    private UrlUtils() {
    }

    /**
     * Extract a specific segment from the url path
     * <p>
     * if index is greater than the total number of segments, null is returned
     *
     * @return segment without '/' prefix
     */
    public static String segment(int index, String path) {
        Assert.isTrue(index >= 0, "index must be greater than or equal to 0");
        Assert.notNull(path, "path == null");

        int plen = path.length(), l = 0, h = plen;

        // remove the leading "/" and following "/"
        if (path.startsWith("/")) l = 1;
        if (path.endsWith("/")) h = h - 1;

        if (l != 0 || h != plen)
            path = path.substring(l, h);

        final String[] segments = path.split("/");
        int len = segments.length;
        if (index >= len)
            return null;

        return segments[index];
    }

    /**
     * Extract a specific segment from the url path
     * <p>
     * if index is greater than the total number of segments, null is returned
     *
     * @return segment with the prefix that is concatenated to it
     */
    public static String segment(int index, String path, String prefix) {
        Assert.notNull(prefix, "prefix == null");
        String segment = segment(index, path);
        if (segment == null)
            return null;
        return prefix + segment;
    }


}

