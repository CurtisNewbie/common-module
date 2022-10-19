package com.curtisnewbie.common.exceptions;


/**
 * Operation Aborted Exception
 *
 * @author yongj.zhuang
 */
public class OperationAbortedException extends IllegalStateException {

    public OperationAbortedException() {
    }

    public OperationAbortedException(String s) {
        super(s);
    }

    public OperationAbortedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationAbortedException(Throwable cause) {
        super(cause);
    }
}
