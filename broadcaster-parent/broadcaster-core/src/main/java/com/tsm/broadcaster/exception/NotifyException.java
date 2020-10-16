package com.tsm.broadcaster.exception;

public class NotifyException extends Exception{
    public NotifyException(String message) {
        super(message);
    }

    public NotifyException(Throwable cause) {
        super(cause);
    }

    public NotifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotifyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
