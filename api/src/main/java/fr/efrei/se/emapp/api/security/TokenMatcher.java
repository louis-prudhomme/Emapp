package fr.efrei.se.emapp.api.security;

import fr.efrei.se.emapp.common.security.Role;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * matches token with their corresponding roles
 */
public class TokenMatcher {
    /**
     * token property file
     */
    private Properties tokenProperties;

    /**
     * list of the parsed token and their roles
     */
    private HashMap<String, Role> roles;

    /**
     * standard constructor ; immediatly reads the token property file
     * @param tokenPropertiesUri path to the token property file
     * @throws IOException if the file cannot be read
     */
    public TokenMatcher(String tokenPropertiesUri) throws IOException {
        this.tokenProperties = new Properties();
        tokenProperties.load(this.getClass().getClassLoader().getResourceAsStream(tokenPropertiesUri));

        this.roles = new HashMap<>();

        readTokens();
    }

    /**
     * parses the tokens and the roles from the file
     */
    private void readTokens() {
        tokenProperties.forEach((o, o2) -> roles.put((String)o2, Role.fromString((String)o)));
    }

    /**
     * tries to match the provided token with an existing and corresponding role
     * @param token provided token
     * @return corresponding role
     */
    public Role getCorrespondingRole(String token) {
        return roles.get(token);
    }
}
