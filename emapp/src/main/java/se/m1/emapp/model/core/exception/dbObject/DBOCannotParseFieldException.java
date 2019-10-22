package se.m1.emapp.model.core.exception.dbObject;

public class DBOCannotParseFieldException extends DBOException {
    public DBOCannotParseFieldException() {
    }

    public DBOCannotParseFieldException(String message) {
        super(message);
    }

    public DBOCannotParseFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBOCannotParseFieldException(Throwable cause) {
        super(cause);
    }

    public DBOCannotParseFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}