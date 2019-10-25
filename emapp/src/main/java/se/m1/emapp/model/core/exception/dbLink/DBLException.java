package se.m1.emapp.model.core.exception.dbLink;

import se.m1.emapp.model.core.exception.DBComException;

public abstract class DBLException extends DBComException {
    public DBLException() {
    }

    public DBLException(String message) {
        super(message);
    }

    public DBLException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBLException(Throwable cause) {
        super(cause);
    }

    public DBLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
