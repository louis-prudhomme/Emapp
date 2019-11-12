package fr.efrei.se.emapp.common.security;

/**
 * Represents the roles attached to the {@link fr.efrei.se.emapp.common.model.CredentialTranscript}
 * Allows the application to authorize users around
 */
public enum Role {
    ADMIN("admin_token"),
    USER("user_token");

    /**
     * Name of the corresponding token in the token property file
     */
    public String tokenName;

    Role(String tokenName) {
        this.tokenName = tokenName;
    }

    /**
     * Parses a {@link Role} from a {@link String} by trying to match it with enums names
     * @param t {@link String} representing the name of the desired {@link Role}
     * @return a matching {@link Role}
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
