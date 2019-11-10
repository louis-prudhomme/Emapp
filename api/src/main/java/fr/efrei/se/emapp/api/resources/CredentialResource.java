package fr.efrei.se.emapp.api.resources;

import com.google.gson.Gson;
import fr.efrei.se.emapp.api.model.core.exception.DBComException;
import fr.efrei.se.emapp.api.model.exception.EmptyParameterException;
import fr.efrei.se.emapp.api.model.exception.EmptyResultException;
import fr.efrei.se.emapp.common.model.CredentialTranscript;

import javax.ws.rs.*;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static fr.efrei.se.emapp.api.resources.ResourceHelper.*;

/**
 * this class is the rest api entry point for every credentials-related request
 * its primary purpose is to authenticate the user and return its profile, including his token
 */
@Path("credentials")
public class CredentialResource {
    /**
     * serialize classes to json
     */
    private Gson gson = new Gson();

    /**
     * authenticates the user using his login and password
     * @param login of the user
     * @param password of the user
     * @return returns a json serizialized profile of the user, including its autorization token level
     * @throws DBComException lel sa va disparètr
     */
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response tryAuth(@FormParam("login")String login, @FormParam("password")String password) throws DBComException {
        try {
            CredentialTranscript c = convertCredential(checkCredentials(login, password));
            return Response.ok(gson.toJson(c)).build();
        } catch (EmptyParameterException e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        } catch (EmptyResultException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
