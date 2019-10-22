package se.m1.emapp.model.core.exception;

public class DBObjectUnreachableConstructor extends DBObjectException {
    public DBObjectUnreachableConstructor(Throwable exception) {
        initCause(exception);
    }
}
