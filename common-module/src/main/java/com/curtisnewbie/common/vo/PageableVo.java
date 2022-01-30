package com.curtisnewbie.common.vo;

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
 * </p>
 *
 * @author yongjie.zhuang
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
     * it's always serialized or deserialized using the name 'payload' (for backward compatibility)
     * </p>
     */
    @JsonProperty(value = "payload")
    private T data = null;

    /**
     * Check whether data is present
     */
    @JsonIgnore
    public boolean isDataPresent() {
        return data != null;
    }

    /**
     * Consumer invoked when data is present
     */
    @JsonIgnore
    public void onDataPresent(Consumer<T> consumer) {
        Assert.notNull(consumer, "consumer == null");
        if (isDataPresent())
            consumer.accept(data);
    }

    /**
     * Consumer invoked when data is absent
     */
    @JsonIgnore
    public void onDataNotPresent(Runnable r) {
        if (!isDataPresent() && r != null)
            r.run();
    }


}
