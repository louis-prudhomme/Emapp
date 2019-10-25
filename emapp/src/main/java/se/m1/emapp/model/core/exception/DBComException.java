package se.m1.emapp.model.core.exception;

public class DBComException extends Exception {
    public DBComException() {
    }

    public DBComException(String message) {
        super(message);
    }

    public DBComException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBComException(Throwable cause) {
        super(cause);
    }

    public DBComException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
