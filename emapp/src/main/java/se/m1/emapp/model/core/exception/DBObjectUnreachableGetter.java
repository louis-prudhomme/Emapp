package se.m1.emapp.model.core.exception;

public class DBObjectUnreachableGetter extends DBObjectException {
    public DBObjectUnreachableGetter(Throwable exception) {
        initCause(exception);
    }
}
