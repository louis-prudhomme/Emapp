package fr.efrei.se.emapp.api.model.core.exception.dbObject;

public class DBOUnreachableConstructor extends DBOUnreachableMethod {
    public DBOUnreachableConstructor(Throwable exception) {
        initCause(exception);
    }
}
