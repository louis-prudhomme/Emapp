package fr.efrei.se.emapp.api.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class TokenMatcher {
    private Properties tokenProperties;

    private HashMap<String, Role> roles;

    public TokenMatcher(String tokenPropertiesUri) throws IOException {
        this.tokenProperties = new Properties();
        tokenProperties.load(this.getClass().getClassLoader().getResourceAsStream(tokenPropertiesUri));
        this.roles = new HashMap<>();

        readTokens();
    }

    private void readTokens() {
        tokenProperties.forEach((o, o2) -> roles.put((String)o2, Role.fromString((String)o)));
    }

    public Role getCorrespondingRole(String token) {
        return roles.get(token);
    }
}
