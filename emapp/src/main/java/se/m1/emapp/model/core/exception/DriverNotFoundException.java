package se.m1.emapp.model.core.exception;

import java.sql.SQLException;

public class DriverNotFoundException extends SQLException {

    public DriverNotFoundException(Throwable exception) {
        initCause(exception);
    }
}
