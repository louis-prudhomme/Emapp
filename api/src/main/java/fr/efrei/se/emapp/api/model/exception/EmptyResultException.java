package fr.efrei.se.emapp.api.model.exception;

/**
 * This exception should fire every time a {@link fr.efrei.se.emapp.api.model}-related operation fails to return a result
 */
public class EmptyResultException extends Exception {
    public EmptyResultException() {
    }

    public EmptyResultException(String message) {
        super(message);
    }

    public EmptyResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyResultException(Throwable cause) {
        super(cause);
    }

    public EmptyResultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
