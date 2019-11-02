package fr.efrei.se.jee.model.core.exception.dbObject;

public class DBOUnreachableGetter extends DBOUnreachableMethod {
    public DBOUnreachableGetter(Throwable exception) {
        initCause(exception);
    }
}
