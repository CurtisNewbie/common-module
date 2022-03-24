package com.curtisnewbie.common.util;

/**
 * Utils for Pagination
 *
 * @author yongj.zhuang
 */
public final class PagingUtils {

    private PagingUtils() {

    }

    /**
     * 拼接 'limit $offset, $limit' 字符串
     */
    public static String limit(long offset, long limit) {
        return String.format("limit %s, %s", offset, limit);
    }

}
