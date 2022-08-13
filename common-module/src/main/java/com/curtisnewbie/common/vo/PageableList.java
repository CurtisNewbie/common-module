package com.curtisnewbie.common.vo;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.curtisnewbie.common.util.*;

import java.util.List;

import static com.curtisnewbie.common.util.PagingUtil.*;

/**
 * Pageable with a list as it's payload
 *
 * @author yongj.zhuang
 */
public class PageableList<T> extends PageableVo<List<T>> {

    public static <T> PageableList<T> from(IPage<T> page) {
        if (page == null) return null;
        PageableList<T> p = new PageableList<>();
        p.setPayload(page.getRecords());
        p.setPagingVo(forIPage(page));
        return p;
    }

    public static <T> PageableList<T> from(List<T> payload, PagingVo pv) {
        PageableList<T> p = new PageableList<>();
        p.setPayload(payload);
        p.setPagingVo(pv);
        return p;
    }

    public static <T> PageableList<T> from(List<T> payload, int page, int total) {
        return from(payload, PagingUtil.ofPageAndTotal(page, total));
    }

}
