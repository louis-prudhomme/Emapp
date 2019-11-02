package fr.efrei.se.jee.model.core.exception;

public class DatabaseCommunicationException extends Exception {
    public DatabaseCommunicationException() {
    }

    public DatabaseCommunicationException(String message) {
        super(message);
    }

    public DatabaseCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseCommunicationException(Throwable cause) {
        super(cause);
    }

    public DatabaseCommunicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
