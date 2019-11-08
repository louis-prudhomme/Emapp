package fr.efrei.se.emapp.common.security;

public enum Role {
    ADMIN("admin_token"),
    USER("user_token");

    public String tokenName;

    Role(String tokenName) {
        this.tokenName = tokenName;
    }

    /**
     * parses from string
     * @param t string
     * @return a role
     */
    public static Role fromString(String t) {
        for (Role r : Role.values()) {
            if (r.tokenName.compareToIgnoreCase(t) == 0) {
                return r;
            }
        }
        return null;
    }
}
