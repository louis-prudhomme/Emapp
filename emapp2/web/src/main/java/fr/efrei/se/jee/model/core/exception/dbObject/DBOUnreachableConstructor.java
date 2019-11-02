package fr.efrei.se.jee.model.core.exception.dbObject;

public class DBOUnreachableConstructor extends DBOUnreachableMethod {
    public DBOUnreachableConstructor(Throwable exception) {
        initCause(exception);
    }
}
