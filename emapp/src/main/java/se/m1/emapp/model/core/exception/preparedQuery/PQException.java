package se.m1.emapp.model.core.exception.preparedQuery;

public abstract class PQException extends Exception {
    public PQException() {
        super();
    }

    public PQException(String message) {
        super(message);
    }

    public PQException(String message, Throwable cause) {
        super(message, cause);
    }

    public PQException(Throwable cause) {
        super(cause);
    }

    protected PQException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
