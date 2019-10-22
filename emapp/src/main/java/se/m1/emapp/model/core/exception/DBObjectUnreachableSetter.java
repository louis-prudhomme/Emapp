package se.m1.emapp.model.core.exception;

public class DBObjectUnreachableSetter extends DBObjectException {
    public DBObjectUnreachableSetter(Throwable exception) {
        initCause(exception);
    }
}
