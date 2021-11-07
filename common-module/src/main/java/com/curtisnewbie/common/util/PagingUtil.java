package com.curtisnewbie.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.curtisnewbie.common.vo.PageablePayloadSingleton;
import com.curtisnewbie.common.vo.PagingVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Utility class for Paging
 *
 * @author yongjie.zhuang
 */
public final class PagingUtil {

    private PagingUtil() {
    }

    /**
     * Convenient method for creating PageInfo wherein the total number of elements is manually specified
     */
    @Deprecated
    public static <T> PageInfo<T> pageInfoOf(List<T> list, long total) {
        PageInfo<T> p = PageInfo.of(list);
        p.setTotal(total);
        return p;
    }

    /**
     * Convert type and wrap result in a {@link PageablePayloadSingleton} which internally contains a {@link PagingVo}
     *
     * @param srcPageInfo source object page info
     * @param converter   function that converts object in T type to V type
     * @param <T>         target's generic type
     * @param <V>         source's generic type
     * @return pageInfo of targetType
     */
    public static <T, V> PageablePayloadSingleton<List<V>> toPageList(IPage<T> srcPageInfo, Function<T, V> converter) {
        PageablePayloadSingleton payloadSingleton = new PageablePayloadSingleton();
        if (srcPageInfo == null) {
            return payloadSingleton;
        }
        // cast to int for backward compatibility
        payloadSingleton.setPagingVo(
                new PagingVo()
                        .ofPage((int) srcPageInfo.getCurrent())
                        .ofTotal(srcPageInfo.getTotal())
        );
        payloadSingleton.setPayload(
                srcPageInfo.getRecords()
                        .stream()
                        .map(converter::apply)
                        .collect(Collectors.toList())
        );
        return payloadSingleton;
    }

    /**
     * Construct {@link Page} parameter
     *
     * @param pageNum current page
     * @param limit   page size
     * @param <T>
     */
    public static <T> Page<T> forPage(int pageNum, int limit) {
        return new Page(pageNum, limit);
    }

    /**
     * Construct {@link Page} parameter
     *
     * @param pv pagingVo
     * @param <T>
     */
    public static <T> Page<T> forPage(PagingVo pv) {
        return forPage(pv.getPage(), pv.getLimit());
    }
}
