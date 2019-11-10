package fr.efrei.se.emapp.common.security;

/**
 * roles of the credentials
 * serves in the user authorization in the rest api
 */
public enum Role {
    ADMIN("admin_token"),
    USER("user_token");

    /**
     * name of the token in the file
     */
    public String tokenName;

    Role(String tokenName) {
        this.tokenName = tokenName;
    }

    /**
     * parses a role from a token name
     * @param t token name
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
