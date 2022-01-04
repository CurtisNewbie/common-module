package com.curtisnewbie.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Vo that is pageable
 * <p>
 * Internally contains a {@link #pagingVo } for pagination
 * </p>
 *
 * @author yongjie.zhuang
 */
@Data
public class PageableVo implements Serializable {

    /**
     * Paging info
     * <p>
     * it's always serialized or deserialized using the name 'pagingVo'
     * </p>
     */
    @JsonProperty(value = "pagingVo")
    private PagingVo pagingVo = new PagingVo();

}
