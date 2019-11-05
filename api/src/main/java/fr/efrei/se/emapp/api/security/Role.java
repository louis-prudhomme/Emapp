package fr.efrei.se.emapp.api.security;

public enum Role {
    ADMIN("admin_token"),
    USER("user_token");

    public String token;

    Role(String token) {
        this.token = token;
    }

    /**
     * parses from string
     * @param t string
     * @return a role
     */
    public static Role fromString(String t) {
        for (Role r : Role.values()) {
            if (r.token.compareToIgnoreCase(t) == 0) {
                return r;
            }
        }
        return null;
    }
}
