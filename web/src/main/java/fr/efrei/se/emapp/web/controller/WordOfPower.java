package fr.efrei.se.emapp.web.controller;

public enum WordOfPower {
    ADD,
    CANCEL,
    COMMIT,
    DELETE,
    DETAILS,
    LOGIN,
    LOGOUT,
    NULL;

    /**
     * parses from string
     * @param s string
     * @return a word
     */
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
