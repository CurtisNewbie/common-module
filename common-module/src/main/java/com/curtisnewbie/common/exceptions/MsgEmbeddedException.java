package com.curtisnewbie.common.exceptions;


/**
 * Exception with message embedded
 *
 * @author yongjie.zhuang
 */
public class MsgEmbeddedException extends Exception {

    /**
     * Custom Message for this exception
     */
    private String msg;

    /**
     * Message in stacktrace
     */
    private String msgInStack;

    /**
     * @param msg message that can be both shown in stacktrace and later retrieved by getter/setter
     */
    public MsgEmbeddedException(String msg) {
        super(msg);
        this.msg = msg;
    }

    /**
     * @param msg message that can be both shown in stacktrace and later retrieved by getter/setter
     * @param e   cause
     */
    public MsgEmbeddedException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    /**
     * @param msgInStacktrace message that will be shown in stacktrace
     * @param customMsg       custom message that can be retrieved by getter/setter
     * @param e               cause
     */
    public MsgEmbeddedException(String msgInStacktrace, String customMsg, Throwable e) {
        super(msgInStacktrace, e);
        this.msg = customMsg;
    }

    public MsgEmbeddedException() {

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
