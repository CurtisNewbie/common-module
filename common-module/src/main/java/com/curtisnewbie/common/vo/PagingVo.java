package com.curtisnewbie.common.vo;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * Info for pagination
 *
 * @author yongjie.zhuang
 */
@Data
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

    /**
     * Set page and return this object
     */
    public PagingVo ofPage(int page) {
        this.page = page;
        return this;
    }

    /**
     * Set limit and return this object
     */
    public PagingVo ofLimit(int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Set total and return this object
     */
    public PagingVo ofTotal(long total) {
        this.total = total;
        return this;
    }

    /**
     * Set total and return this object
     */
    public PagingVo ofPageInfoTotal(PageInfo<?> pi){
        this.total = pi.getTotal();
        return this;
    }

    /**
     * Set total and return this object
     */
    public PagingVo ofTotal(PageInfo<?> pi) {
        this.total = pi.getTotal();
        return this;
    }

    /**
     * Increment page number
     */
    public void nextPage() {
        if (this.page == null)
            throw new IllegalStateException("this.page == null");
        ++this.page;
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
