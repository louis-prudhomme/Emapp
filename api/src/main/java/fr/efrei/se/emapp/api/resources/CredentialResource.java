package fr.efrei.se.emapp.api.resources;

import com.google.gson.Gson;
import fr.efrei.se.emapp.api.model.business.Credential;
import fr.efrei.se.emapp.api.model.core.JPAManager;
import fr.efrei.se.emapp.api.model.exception.EmptyParameterException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    @EJB
    private JPAManager jpaManager = new JPAManager();

    /**
     * authenticates the user using his login and password
     * @param login of the user
     * @param password of the user
     * @return returns a json serizialized profile of the user, including its autorization token level
     */
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    //todo tranform parameters in credential
    public Response tryAuth(@FormParam("login")String login, @FormParam("password")String password) {
        try {
            Credential credential = new Credential(login, password);

            //todo fix
            if (jpaManager.checkCredentials(credential)) {
                return Response.ok(gson.toJson(ResourceHelper.convertCredential(credential))).build();
            } else {
                throw new EmptyParameterException();
            }
        } catch (EmptyParameterException e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        } /*catch (EmptyResultException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }*/
    }
}
