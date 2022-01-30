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
    private String embeddedMsg;

    public UnrecoverableMsgEmbeddedException(String msg) {
        super(msg);
        this.embeddedMsg = msg;
    }

    /**
     * @param embeddedMsg message that can be both shown in stacktrace and later retrieved by getter/setter
     * @param e          cause
     */
    public UnrecoverableMsgEmbeddedException(String embeddedMsg, Throwable e) {
        super(embeddedMsg, e);
        this.embeddedMsg = embeddedMsg;
    }

    /**
     * @param msgInStacktrace message that will be shown in stacktrace
     * @param customMsg       custom message that can be retrieved by getter/setter
     * @param e               cause
     */
    public UnrecoverableMsgEmbeddedException(String msgInStacktrace, String customMsg, Throwable e) {
        super(msgInStacktrace, e);
        this.embeddedMsg = customMsg;
    }

    public UnrecoverableMsgEmbeddedException() {

    }

    public String getEmbeddedMsg() {
        return embeddedMsg;
    }

    public void setEmbeddedMsg(String embeddedMsg) {
        this.embeddedMsg = embeddedMsg;
    }
}
