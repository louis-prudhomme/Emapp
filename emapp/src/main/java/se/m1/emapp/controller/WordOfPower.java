package se.m1.emapp.controller;

public enum WordOfPower {
    ADD,
    CANCEL,
    COMMIT,
    DELETE,
    DETAILS,
    LOGIN,
    LOGOUT,
    NULL;

    public static WordOfPower fromString(String s) {
        if(s == null) return NULL;
        for (WordOfPower w : WordOfPower.values()) {
            if (w.name().compareToIgnoreCase(s) == 0) {
                return w;
            }
        }
        return NULL;
    }
}
