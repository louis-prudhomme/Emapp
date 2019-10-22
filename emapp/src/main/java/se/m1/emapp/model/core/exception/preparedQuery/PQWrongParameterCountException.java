package se.m1.emapp.model.core.exception.preparedQuery;

public class PQWrongParameterCountException extends PQWrongParameterException {
    public PQWrongParameterCountException() {
    }

    public PQWrongParameterCountException(String message) {
        super(message);
    }

    public PQWrongParameterCountException(String message, Throwable cause) {
        super(message, cause);
    }

    public PQWrongParameterCountException(Throwable cause) {
        super(cause);
    }

    public PQWrongParameterCountException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
