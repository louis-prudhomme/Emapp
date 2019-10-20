package se.m1.emapp.model.exception;

public class DBObjectUnreachableGetter extends DBObjectException {
    public DBObjectUnreachableGetter(Throwable exception) {
        initCause(exception);
    }
}
