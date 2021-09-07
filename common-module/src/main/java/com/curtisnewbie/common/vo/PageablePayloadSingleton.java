package com.curtisnewbie.common.vo;

import lombok.Data;

/**
 * <p>
 * A pageable value object with a single payload object
 * </p>
 * <p>
 * Convenient payload wrapper for VO that needs {@link PagingVo}
 * </p>
 *
 * @author yongjie.zhuang
 */
@Data
public class PageablePayloadSingleton<T> extends PageableVo {

    private T payload;

}
