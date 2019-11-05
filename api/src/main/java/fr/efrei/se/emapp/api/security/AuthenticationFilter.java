package fr.efrei.se.emapp.api.security;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    private static final String AUTH_STATIC_SALT = "MAHOUTS";
    private static final String TOKEN_FILE = "token.properties";

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
/*
        if(!isValidToken(authorizationHeader)) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }*/

        Method resourceClass = resourceInfo.getResourceMethod();
        List<Role> authorizedRoles = extractRoles(resourceClass);
        //String token = authorizationHeader.substring(AUTH_STATIC_SALT.length()).trim();
        String token = authorizationHeader;

        if(!authorizedRoles.isEmpty()) {
            if(!checkPermission(authorizedRoles, token)) {
                containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }
    }

    private boolean checkPermission(List<Role> authorizedRoles, String token) throws IOException {
        TokenMatcher matcher = new TokenMatcher(TOKEN_FILE);
        Role incomingRole = matcher.getCorrespondingRole(token);

        return authorizedRoles.contains(incomingRole);
    }

    private boolean isValidToken(String authorizationHeader) {
        return authorizationHeader != null &&
                authorizationHeader.toLowerCase().startsWith(AUTH_STATIC_SALT.toLowerCase() + " ");
    }

    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<>();
            } else {
                Role[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }
}
