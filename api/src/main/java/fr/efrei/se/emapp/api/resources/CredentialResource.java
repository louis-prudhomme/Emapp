package fr.efrei.se.emapp.api.resources;

import com.google.gson.Gson;
import fr.efrei.se.emapp.api.model.business.Credential;
import fr.efrei.se.emapp.api.model.core.JPAManager;
import fr.efrei.se.emapp.api.model.exception.EmptyParameterException;
import fr.efrei.se.emapp.api.model.exception.EmptyResultException;
import fr.efrei.se.emapp.common.model.CredentialTranscript;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This class is the REST API entry point for every {@link Credential}-related request
 * Its primary purpose is to authenticate the user and return its profile, including his security token
 */
@Path("credentials")
public class CredentialResource {
    /**
     * {@link Gson} class to serialize and deserialize the incoming parameters and the answers
     */
    private Gson gson = new Gson();

    /**
     * {@link JPAManager} class to manage the {@link fr.efrei.se.emapp.api.model} classes
     */
    @EJB
    private JPAManager jpaManager = new JPAManager();

    /**
     * Authenticates an incoming user’s login and password against the database
     * @param user {@link CredentialTranscript} containing the login and the password intel
     * @return a JSON-serialized version of a {@link CredentialTranscript} containing all the user’s information if he exists
     */
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response tryAuth(@FormParam("user")CredentialTranscript user) {
        try {
            Credential credential = jpaManager.checkCredentials(ResourceHelper.convertCredentialTranscript(user));
            return Response.ok(gson.toJson(ResourceHelper.convertCredential(credential))).build();
        } catch (EmptyParameterException e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        } catch (EmptyResultException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
