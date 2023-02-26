package com.rks.mcommon.exceptions;

public class TooManyRequestsException extends BaseException {

    private static final long serialVersionUID = -1057807218758703057L;

    public TooManyRequestsException(String status, int code, String message) {
        super(status, code, message);
    }

    public TooManyRequestsException(String message, String status, int code, String message1) {
        super(message, status, code, message1);
    }

    public TooManyRequestsException(String message, Throwable cause, String status, int code,
                                    String message1) {
        super(message, cause, status, code, message1);
    }

}
