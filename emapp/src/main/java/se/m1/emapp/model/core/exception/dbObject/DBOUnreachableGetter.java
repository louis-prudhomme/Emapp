package se.m1.emapp.model.core.exception.dbObject;

public class DBOUnreachableGetter extends DBOUnreachableMethod {
    public DBOUnreachableGetter(Throwable exception) {
        initCause(exception);
    }
}
