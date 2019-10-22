package se.m1.emapp.model.core.exception.dbObject;

public class DBOUnreachableSetter extends DBOUnreachableMethod {
    public DBOUnreachableSetter(Throwable exception) {
        initCause(exception);
    }
}
