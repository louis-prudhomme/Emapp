package fr.efrei.se.jee.model.core.exception.dbLink;

import fr.efrei.se.jee.model.core.exception.DatabaseCommunicationException;

public abstract class DBLException extends DatabaseCommunicationException {
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
