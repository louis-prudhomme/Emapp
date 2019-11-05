package fr.efrei.se.emapp.api.model.core.exception.preparedQuery;

public class PQWrongParameterException extends PQException {
    public PQWrongParameterException() {
        super();
    }

    public PQWrongParameterException(String message) {
        super(message);
    }

    public PQWrongParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public PQWrongParameterException(Throwable cause) {
        super(cause);
    }

    protected PQWrongParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
