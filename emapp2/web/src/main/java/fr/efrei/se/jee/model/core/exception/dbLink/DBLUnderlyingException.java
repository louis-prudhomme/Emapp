package fr.efrei.se.jee.model.core.exception.dbLink;

public class DBLUnderlyingException extends DBLException {
    public DBLUnderlyingException() {
        super();
    }

    public DBLUnderlyingException(String message) {
        super(message);
    }

    public DBLUnderlyingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBLUnderlyingException(Throwable cause) {
        super(cause);
    }

    public DBLUnderlyingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
