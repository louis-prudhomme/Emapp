package fr.efrei.se.emapp.web.security;

import fr.efrei.se.emapp.common.security.Role;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * matches role enumerations with actual tokens
 */
public class RoleMatcher {
    /**
     * token property file
     */
    private Properties tokenProperties;

    /**
     * parsed roles and corrresponding tokens
     */
    private HashMap<Role, String> tokens;

    /**
     * standard constructor ; immediatly reads the token property file
     * @param tokenPropertiesUri path to the token property file
     * @throws IOException when it cannot read the file (usually, wrong path)
     */
    public RoleMatcher(String tokenPropertiesUri) throws IOException {
        this.tokenProperties = new Properties();
        tokenProperties.load(this.getClass().getClassLoader().getResourceAsStream(tokenPropertiesUri));

        this.tokens = new HashMap<>();

        readRoles();
    }

    /**
     * reads the roles from the property file
     */
    private void readRoles() {
        tokenProperties.forEach((o, o2) -> tokens.put(Role.fromString((String)o), (String)o2));
    }

    /**
     * returns the token corresponding to a sepcific role
     * @param role for which the token is wanted
     * @return string-form token
     */
    public String getCorrespondingToken(Role role) {
        return tokens.get(role);
    }
}
