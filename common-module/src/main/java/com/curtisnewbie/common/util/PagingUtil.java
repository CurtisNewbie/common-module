package com.curtisnewbie.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.curtisnewbie.common.vo.PageablePayloadSingleton;
import com.curtisnewbie.common.vo.PageableVo;
import com.curtisnewbie.common.vo.PagingVo;
import lombok.Builder;
import org.springframework.util.Assert;

import java.awt.print.Pageable;
import java.util.List;
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
     * Convert type and wrap result in a {@link com.curtisnewbie.common.vo.PageableVo} which internally contains a
     * {@link PagingVo}
     *
     * @param srcPageInfo source object page info
     * @param converter   function that converts object in T type to V type
     * @param <T>         target's generic type
     * @param <V>         source's generic type
     * @return pageInfo of targetType
     */
    public static <T, V> PageableVo<List<V>> toPageable(IPage<T> srcPageInfo, Function<T, V> converter) {
        if (srcPageInfo == null) {
            return new PageableVo();
        }
        final PageableVo<List<V>> p = new PageableVo<>();
        p.setPagingVo(forIPage(srcPageInfo));
        p.setData(
                srcPageInfo.getRecords()
                        .stream()
                        .map(converter::apply)
                        .collect(Collectors.toList())
        );
        return p;
    }

    /**
     * Convert PageableVo with given converter
     */
    public static <T, V> PageableVo<List<V>> convert(PageableVo<List<T>> srcPv, Function<T, V> converter) {
        if (srcPv == null) {
            return new PageableVo();
        }
        final PageableVo<List<V>> p = new PageableVo<>();
        p.setPagingVo(srcPv.getPagingVo());
        if (srcPv.getData() != null) {
            p.setData(
                    srcPv.getData()
                            .stream()
                            .map(converter::apply)
                            .collect(Collectors.toList())
            );
        }
        return p;
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
     * Construct {@link PagingVo} based on given {@link IPage}
     */
    public static PagingVo forIPage(IPage p) {
        if (p == null) return null;

        return new PagingVo()
                .ofPage((int) p.getCurrent()) // cast to int for backward compatibility
                .ofTotal(p.getTotal());
    }

    /**
     * Construct {@link Page} parameter based on give {@link PagingVo}
     *
     * @param pv  pagingVo
     * @param <T>
     */
    public static <T> Page<T> forPage(PagingVo pv) {
        return forPage(pv.getPage(), pv.getLimit());
    }

    /**
     * Same as {@link PagingUtil#toPageable(IPage, Function)}, but different style of coding and potentially better
     * readability (especially if lots of lambda expression are used)
     *
     * @param <T>
     * @param <V>
     */
    public static class PageableVoConstructor<T, V> {

        private IPage<T> srcPageInfo;
        private Function<T, V> converter;

        public PageableVoConstructor<T, V> setSrcPageInfo(IPage<T> srcPageInfo) {
            this.srcPageInfo = srcPageInfo;
            return this;
        }

        public PageableVoConstructor<T, V> setConverter(Function<T, V> converter) {
            this.converter = converter;
            return this;
        }

        public PageableVo<List<V>> convertAndBuild() {
            Assert.notNull(srcPageInfo, "srcPageInfo == null");
            Assert.notNull(converter, "converter == null");
            return toPageable(srcPageInfo, converter);
        }
    }
}
