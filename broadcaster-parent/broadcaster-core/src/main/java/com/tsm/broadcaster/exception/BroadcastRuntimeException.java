package com.tsm.broadcaster.exception;

public class BroadcastRuntimeException extends RuntimeException {
    public BroadcastRuntimeException(String message) {
        super(message);
    }

    public BroadcastRuntimeException(Throwable cause) {
        super(cause);
    }

    public BroadcastRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BroadcastRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
