package fr.efrei.se.emapp.api.security;

import fr.efrei.se.emapp.common.security.Role;

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

import static fr.efrei.se.emapp.common.utils.Constants.TOKEN_FILE;

/**
 * acts as a filter before actual handling of the http request by the rest api
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
    /**
     *
     */
    @Context
    private ResourceInfo resourceInfo;

    /**
     * main method ; security handler that verifies the authorization level of the request (via token)
     * @param containerRequestContext the request context
     * @throws IOException when the token property file cannot be read
     */
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        Method resourceClass = resourceInfo.getResourceMethod();
        List<Role> authorizedRoles = extractRoles(resourceClass);

        //basically, this method does nothing except if there is a problem
        //if there are actual security concern
        if(!authorizedRoles.isEmpty()) {
            //and if the security authorization are not observed
            if(!checkPermission(authorizedRoles, authorizationHeader)) {
                //returns http 401 error
                containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
            }
        }
    }

    /**
     * tries to match the provided token with a list of authorized roles
     * @param authorizedRoles list of the authorized roles
     * @param token provided token
     * @return true if the token corresponds to an existing and autorized role
     * @throws IOException is the token proerty file cannot be read
     */
    private boolean checkPermission(List<Role> authorizedRoles, String token) throws IOException {
        TokenMatcher matcher = new TokenMatcher(TOKEN_FILE);
        Role incomingRole = matcher.getCorrespondingRole(token);

        return authorizedRoles.contains(incomingRole);
    }

    /**
     * extracts the authorized roles from the context of the called method
     * @param annotatedElement class from which originates the called method
     * @return a list of the authorized roles
     */
    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<>();
        } else {
            //the secured annotation contains the authorized roles
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
