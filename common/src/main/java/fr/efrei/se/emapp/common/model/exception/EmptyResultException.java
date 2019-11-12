package fr.efrei.se.emapp.common.model.exception;

import java.io.IOException;

/**
 * This exception should fire every time a {@link fr.efrei.se.emapp.api.model}-related operation fails to return a result
 */
public class EmptyResultException extends IOException {
    public EmptyResultException() {
    }

    public EmptyResultException(String message) {
        super(message);
    }

}
