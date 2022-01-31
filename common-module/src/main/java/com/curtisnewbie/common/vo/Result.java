package com.curtisnewbie.common.vo;

import com.curtisnewbie.common.exceptions.UnrecoverableMsgEmbeddedException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * Result for frontend communication
 *
 * @author yongjie.zhuang
 */
@Data
public class Result<T> implements Serializable {

    /** message being returned */
    private String msg;

    /** whether current response has an error */
    private boolean hasError;

    /** data */
    private T data;

    public static <T> Result<T> ok() {
        Result<T> resp = new Result<T>();
        resp.hasError = false;
        resp.msg = "";
        resp.data = null;
        return resp;
    }

    public static <T> Result<T> of(T data) {
        Result<T> resp = new Result<T>();
        resp.hasError = false;
        resp.msg = null;
        resp.data = data;
        return resp;
    }

    public static <T> Result<T> error(String errMsg) {
        Result<T> resp = new Result<T>();
        resp.hasError = true;
        resp.msg = errMsg;
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
        return !hasError;
    }

    /**
     * Assert {@link #isOk()}, throw exception if it's not
     *
     * @throws UnrecoverableMsgEmbeddedException
     */
    @JsonIgnore
    public void assertIsOk() {
        if (!isOk())
            throw new UnrecoverableMsgEmbeddedException(msg);
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

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", hasError=" + hasError +
                ", data=" + data +
                '}';
    }
}
