package fr.efrei.se.jee.model.core.exception.dbObject;

public class DBOUnreachableSetter extends DBOUnreachableMethod {
    public DBOUnreachableSetter(Throwable exception) {
        initCause(exception);
    }
}
