package se.m1.emapp.model.core.exception.dbObject;

public abstract class DBOUnreachableMethod extends DBOException {
    public DBOUnreachableMethod() {
    }

    public DBOUnreachableMethod(String message) {
        super(message);
    }

    public DBOUnreachableMethod(String message, Throwable cause) {
        super(message, cause);
    }

    public DBOUnreachableMethod(Throwable cause) {
        super(cause);
    }

    public DBOUnreachableMethod(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
