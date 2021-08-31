package com.curtisnewbie.common.exceptions;


/**
 * Exception with message embedded that is not recoverable
 *
 * @author yongjie.zhuang
 */
public class UnrecoverableMsgEmbeddedException extends RuntimeException {

    /**
     * Custom Message for this exception
     */
    private String msg;

    /**
     * @param msg message that can be both shown in stacktrace and later retrieved by getter/setter
     */
    public UnrecoverableMsgEmbeddedException(String msg) {
        super(msg);
        this.msg = msg;
    }

    /**
     * @param msg message that can be both shown in stacktrace and later retrieved by getter/setter
     * @param e   cause
     */
    public UnrecoverableMsgEmbeddedException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    /**
     * @param msgInStacktrace message that will be shown in stacktrace
     * @param customMsg       custom message that can be retrieved by getter/setter
     * @param e               cause
     */
    public UnrecoverableMsgEmbeddedException(String msgInStacktrace, String customMsg, Throwable e) {
        super(msgInStacktrace, e);
        this.msg = customMsg;
    }

    public UnrecoverableMsgEmbeddedException() {

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
