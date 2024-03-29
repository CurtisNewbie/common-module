package com.curtisnewbie.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.curtisnewbie.common.vo.PageableList;
import com.curtisnewbie.common.vo.PagingVo;

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
     * Concatenate 'LIMIT $offset, $limit' string
     */
    public static String limit(long offset, long limit) {
        return String.format("LIMIT %s, %s", offset, limit);
    }

    /**
     * Concatenate 'LIMIT $offset, $limit' string
     */
    public static String limit(PagingVo p) {
        final long page = p.getPage();
        final long limit = p.getLimit();
        final long offset = page <= 0 ? 0 : (page - 1L) * limit;
        return limit(offset, limit);
    }

    /**
     * Convert type of payload
     * <p>
     * Conversion is achieved automatically using {@link BeanCopyUtils#toType(Object, Class)}
     *
     * @param from      source object page info
     * @param targetClz target class
     * @param <T>       target's generic type
     * @param <V>       source's generic type
     * @return pageInfo of targetType
     */
    public static <T, V> PageableList<V> convertPayload(PageableList<T> from, Class<V> targetClz) {
        return convertPayload(from, t -> BeanCopyUtils.toType(t, targetClz));
    }

    /**
     * Convert type of payload
     *
     * @param from      source object page info
     * @param converter function that converts object in T type to V type
     * @param <T>       target's generic type
     * @param <V>       source's generic type
     * @return pageInfo of targetType
     */
    public static <T, V> PageableList<V> convertPayload(PageableList<T> from, Function<T, V> converter) {
        if (from == null) return new PageableList<>();

        final PageableList<V> to = new PageableList<>();
        to.setPagingVo(from.getPagingVo());
        from.onPayloadPresent(payload ->
                to.setPayload(payload.stream()
                        .map(converter)
                        .collect(Collectors.toList())));
        return to;
    }

    /**
     * Build {@link PageableList}
     *
     * @param srcPageInfo source object page info
     * @param converter   function that converts object in T type to V type
     * @param <T>         target's generic type
     * @param <V>         source's generic type
     * @return pageInfo of targetType
     */
    public static <T, V> PageableList<V> toPageableList(IPage<T> srcPageInfo, Function<T, V> converter) {
        if (srcPageInfo == null) return new PageableList<>();
        final PageableList<V> p = new PageableList<>();
        p.setPagingVo(forIPage(srcPageInfo));
        if (srcPageInfo.getRecords() != null)
            p.setPayload(
                    srcPageInfo.getRecords()
                            .stream()
                            .map(converter)
                            .collect(Collectors.toList())
            );
        return p;
    }

    /**
     * Build {@link PageableList} from {@link IPage}
     */
    public static <T, V> PageableList<V> toPageableList(IPage<V> page) {
        if (page == null) return new PageableList<>();
        return PageableList.from(page);
    }

    /**
     * Construct {@link Page} parameter
     *
     * @param pageNum current page
     * @param limit   page size
     */
    public static <T> Page<T> forPage(int pageNum, int limit) {
        return new Page<>(pageNum, limit);
    }

    /**
     * Construct {@link PagingVo} based on given {@link IPage}
     */
    public static PagingVo forIPage(IPage<?> p) {
        if (p == null) return null;

        return ofPageAndTotal((int) p.getCurrent(), p.getTotal());
    }

    /**
     * Construct {@link PagingVo} based on page and total number of records
     */
    public static PagingVo ofPageAndTotal(int page, long total) {
        return new PagingVo()
                .ofPage(page)
                .ofTotal(total);
    }

    /**
     * Construct {@link Page} parameter based on give {@link PagingVo}
     *
     * @param pv pagingVo
     */
    public static <T> Page<T> forPage(PagingVo pv) {
        return forPage(pv.getPage(), pv.getLimit());
    }
}
