package fr.efrei.se.emapp.web.security;

import fr.efrei.se.emapp.common.security.Role;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class RoleMatcher {
    private Properties tokenProperties;

    private HashMap<Role, String> tokens;

    public RoleMatcher(String tokenPropertiesUri) throws IOException {
        this.tokenProperties = new Properties();
        tokenProperties.load(this.getClass().getClassLoader().getResourceAsStream(tokenPropertiesUri));

        this.tokens = new HashMap<>();

        readRoles();
    }

    private void readRoles() {
        tokenProperties.forEach((o, o2) -> tokens.put(Role.fromString((String)o), (String)o2));
    }

    public String getCorrespondingToken(Role role) {
        return tokens.get(role);
    }
}
