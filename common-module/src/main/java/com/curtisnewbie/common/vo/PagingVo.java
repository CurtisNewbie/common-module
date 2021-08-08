package com.curtisnewbie.common.vo;

import java.io.Serializable;

/**
 * Info for pagination
 *
 * @author yongjie.zhuang
 */
public class PagingVo implements Serializable {

    /**
     * Size of a page
     */
    private Integer limit = 10;

    /**
     * Page number
     */
    private Integer page = 1;

    /**
     * Total items
     */
    private Long total;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PagingVo{" +
                "limit=" + limit +
                ", page=" + page +
                ", total=" + total +
                '}';
    }
}
