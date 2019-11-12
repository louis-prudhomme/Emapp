package fr.efrei.se.emapp.common.model.exception;

import java.io.IOException;

/**
 * This exception should fire every time a {@link fr.efrei.se.emapp.api.model}-related operation is executed without parameters
 */
public class WrongParameterException extends IOException {
    public WrongParameterException() {
    }

    public WrongParameterException(String message) {
        super(message);
    }

}
