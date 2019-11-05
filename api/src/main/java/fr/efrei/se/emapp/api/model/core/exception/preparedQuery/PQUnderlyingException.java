package fr.efrei.se.emapp.api.model.core.exception.preparedQuery;

public class PQUnderlyingException extends PQException {
    public PQUnderlyingException() {
    }

    public PQUnderlyingException(String message) {
        super(message);
    }

    public PQUnderlyingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PQUnderlyingException(Throwable cause) {
        super(cause);
    }

    public PQUnderlyingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
