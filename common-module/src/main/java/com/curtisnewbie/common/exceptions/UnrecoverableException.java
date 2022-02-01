package com.curtisnewbie.common.exceptions;


import lombok.Data;

/**
 * Exception with message embedded and error code
 *
 * @author yongjie.zhuang
 */
@Data
public class UnrecoverableException extends IllegalStateException {

    /**
     * Custom Message for this exception
     */
    private final String embeddedMsg;

    /**
     * Error code
     */
    private final String errorCode;

    public UnrecoverableException(String msg) {
        super(msg);
        this.embeddedMsg = msg;
        this.errorCode = null;
    }

    public UnrecoverableException(String msg, String errorCode) {
        super(msg);
        this.embeddedMsg = msg;
        this.errorCode = errorCode;
    }

    public UnrecoverableException(String msg, Throwable e) {
        super(msg, e);
        this.embeddedMsg = msg;
        this.errorCode = null;
    }

    public UnrecoverableException(String msg, String errorCode, Throwable e) {
        super(msg, e);
        this.embeddedMsg = msg;
        this.errorCode = errorCode;
    }

    public UnrecoverableException() {
        this.embeddedMsg = null;
        this.errorCode = null;
    }
}
