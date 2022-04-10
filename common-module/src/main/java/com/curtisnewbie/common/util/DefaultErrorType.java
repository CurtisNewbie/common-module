package com.curtisnewbie.common.util;

/**
 * Default implementation of ErrorType, one may decide to use enum instead
 *
 * @author yongj.zhuang
 */
public class DefaultErrorType implements ErrorType {

    private final String code;
    private final String msg;

    public DefaultErrorType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
