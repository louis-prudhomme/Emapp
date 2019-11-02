package fr.efrei.se.emapp.api.model.core.exception.dbObject;

public class DBOUnreachableSetter extends DBOUnreachableMethod {
    public DBOUnreachableSetter(Throwable exception) {
        initCause(exception);
    }
}
