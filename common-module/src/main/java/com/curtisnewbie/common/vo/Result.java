package com.curtisnewbie.common.vo;

import com.curtisnewbie.common.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Result for REST Endpoints
 *
 * @author yongjie.zhuang
 */
@Slf4j
@Data
public class Result<T> implements Serializable {

    /** Error code */
    private String errorCode;

    /** message being returned */
    private String msg;

    /** whether current response has an error */
    private boolean error;

    /** data */
    private T data;

    public static <T> Result<T> ok() {
        return Result.of(null);
    }

    public static <T> Result<T> runThenOk(Runnable r) {
        r.run();
        return ok();
    }

    public static <T> Result<T> ofSupplied(Supplier<T> dataSupplier) {
        return of(dataSupplier.get());
    }

    public static <T> Result<T> of(T data) {
        Result<T> resp = new Result<T>();
        resp.error = false;
        resp.msg = null;
        resp.data = data;
        return resp;
    }

    public static <T> Result<T> error(String errMsg) {
        Result<T> resp = new Result<T>();
        resp.error = true;
        resp.msg = errMsg;
        resp.data = null;
        return resp;
    }

    public static <T> Result<T> error(String errMsg, String errorCode) {
        Result<T> resp = new Result<T>();
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
        if (errorType == null || !isError()) return false;

        return Objects.equals(errorCode, errorType.getCode());
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * Try to get data from Result, throw exception if failed
     */
    public static <T> T tryGetData(Result<T> r) {
        return tryGetData(r, null);
    }

    /**
     * Try to get data from Result, throw exception if failed
     */
    public static <T> T tryGetData(Result<T> r, @Nullable Supplier<String> descSupplier) {
        Objects.requireNonNull(r);
        String desc = descSupplier != null ? descSupplier.get() : null;
        if (r.isError()) {
            if (desc != null)
                log.error("Failed to invoke endpoint ('{}'), code: {}, msg: {}", desc, r.errorCode, r.msg);
            else
                log.error("Failed to invoke endpoint, code: {}, msg: {}", r.errorCode, r.msg);

            // throw exception
            r.assertIsOk();
        }
        return r.getData();
    }

}
