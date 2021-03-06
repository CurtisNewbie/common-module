package com.curtisnewbie.common.vo;

import com.curtisnewbie.common.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Result for REST Endpoints
 *
 * @author yongjie.zhuang
 */
@Data
public class Result<T> implements Serializable {

    /** Error code */
    private String errorCode;

    /** message being returned */
    private String msg;

    /** whether current response has an error */
    private boolean error;

    @Deprecated // todo change to error, so that we don't have a setter method called 'setHasError' :(
    /** whether current response has an error */
    private boolean hasError;

    /** data */
    private T data;

    public static <T> Result<T> ok() {
        Result<T> resp = new Result<T>();
        resp.hasError = false;
        resp.error = false;
        resp.msg = "";
        resp.data = null;
        return resp;
    }

    public static <T> Result<T> of(T data) {
        Result<T> resp = new Result<T>();
        resp.hasError = false;
        resp.error = false;
        resp.msg = null;
        resp.data = data;
        return resp;
    }

    public static <T> Result<T> error(String errMsg) {
        Result<T> resp = new Result<T>();
        resp.hasError = true;
        resp.error = true;
        resp.msg = errMsg;
        resp.data = null;
        return resp;
    }

    public static <T> Result<T> error(String errMsg, String errorCode) {
        Result<T> resp = new Result<T>();
        resp.hasError = true;
        resp.error = true;
        resp.msg = errMsg;
        resp.errorCode = errorCode;
        resp.data = null;
        return resp;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isOk() {
        return !isError();
    }

    /**
     * Assert {@link #isOk()}, throw exception if it's not
     */
    @JsonIgnore
    public void assertIsOk() {
        AssertUtils.isTrue(isOk(), new DefaultErrorType(errorCode, msg));
    }

    /**
     * Check if it's the given ErrorType
     */
    @JsonIgnore
    public boolean isErrorType(final ErrorType errorType) {
        if (errorType == null)
            return false;
        if (!hasError())
            return false;
        return Objects.equals(errorCode, errorType.getCode());
    }

    public boolean hasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
