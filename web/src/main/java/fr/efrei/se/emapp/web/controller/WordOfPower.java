package fr.efrei.se.emapp.web.controller;

/**
 * Represents the user input
 * It is non-dependant of the application’s state
 */
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
     * Parses a {@link WordOfPower} from a {@link String} by trying to match it with enums names
     * @param s {@link String} representing the name of the desired {@link WordOfPower}
     * @return a matching {@link WordOfPower}
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
