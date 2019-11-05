package fr.efrei.se.emapp.api.model.core.exception.preparedQuery;

import fr.efrei.se.emapp.api.model.core.exception.DBComException;

public abstract class PQException extends DBComException {
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
