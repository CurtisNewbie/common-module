package com.curtisnewbie.common.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.*;
import com.curtisnewbie.common.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 * Vo that is pageable
 * <p>
 * Internally contains a {@link #pagingVo } for pagination
 * <p>
 *
 * @author yongjie.zhuang
 * @see PageableList
 */
@Data
public class PageableVo<T> implements Serializable {

    /**
     * Paging info
     * <p>
     * it's always serialized or deserialized using the name 'pagingVo'
     * </p>
     */
    @JsonProperty(value = "pagingVo")
    private PagingVo pagingVo = new PagingVo();

    /**
     * Nullable Payload
     * <p>
     * it's always serialized or deserialized using the name 'payload'
     * </p>
     */
    @JsonProperty(value = "payload")
    private T payload = null;

    /**
     * Check whether data is present
     */
    @JsonIgnore
    public boolean isPayloadPresent() {
        return payload != null;
    }

    /**
     * Consumer invoked when payload is present
     */
    @JsonIgnore
    public void onPayloadPresent(Consumer<T> consumer) {
        Assert.notNull(consumer, "consumer == null");
        if (isPayloadPresent())
            consumer.accept(payload);
    }

    /**
     * Consumer invoked when data is absent
     */
    @JsonIgnore
    public void onPayloadNotPresent(Runnable r) {
        if (!isPayloadPresent() && r != null)
            r.run();
    }

    /**
     * Build a {@link Page} using {@link #pagingVo}
     */
    @JsonIgnore
    public Page page() {
        return PagingUtil.forPage(this.getPagingVo());
    }

}
