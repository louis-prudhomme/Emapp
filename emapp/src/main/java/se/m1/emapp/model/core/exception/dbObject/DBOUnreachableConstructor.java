package se.m1.emapp.model.core.exception.dbObject;

public class DBOUnreachableConstructor extends DBOUnreachableMethod {
    public DBOUnreachableConstructor(Throwable exception) {
        initCause(exception);
    }
}
