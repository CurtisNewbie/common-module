package com.curtisnewbie.common.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
     * it's always serialized or deserialized using the name 'data'
     * </p>
     */
    @JsonProperty(value = "data")
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
        if (isDataPresent())
            consumer.accept(data);
    }

}
