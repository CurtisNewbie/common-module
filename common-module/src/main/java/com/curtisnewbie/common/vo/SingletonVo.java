package com.curtisnewbie.common.vo;

import lombok.Data;

/**
 * Object with single value
 *
 * @author yongj.zhuang
 */
@Data
public class SingletonVo<T> {

    private T value;

}
