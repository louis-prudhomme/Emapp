package fr.efrei.se.emapp.api.model.core.exception.dbObject;

public class DBOUnreachableGetter extends DBOUnreachableMethod {
    public DBOUnreachableGetter(Throwable exception) {
        initCause(exception);
    }
}