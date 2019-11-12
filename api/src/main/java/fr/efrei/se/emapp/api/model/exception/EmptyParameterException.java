package fr.efrei.se.emapp.api.model.exception;

/**
 * This exception should fire every time a {@link fr.efrei.se.emapp.api.model}-related operation is executed without parameters
 */
public class EmptyParameterException extends Exception {
    public EmptyParameterException() {
    }

    public EmptyParameterException(String message) {
        super(message);
    }

    public EmptyParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyParameterException(Throwable cause) {
        super(cause);
    }

    public EmptyParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
