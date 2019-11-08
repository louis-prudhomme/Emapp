package fr.efrei.se.emapp.api.model.core.exception.dbLink;

public class DBLDriverNotFoundException extends DBLException {
    public DBLDriverNotFoundException() {
    }

    public DBLDriverNotFoundException(String message) {
        super(message);
    }

    public DBLDriverNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBLDriverNotFoundException(Throwable cause) {
        super(cause);
    }

    public DBLDriverNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}