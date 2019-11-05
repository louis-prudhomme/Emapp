package fr.efrei.se.emapp.api.resources;

import com.google.gson.Gson;
import fr.efrei.se.emapp.api.model.business.Credential;
import fr.efrei.se.emapp.api.model.core.exception.DBComException;
import fr.efrei.se.emapp.api.model.exception.EmptyParameterException;
import fr.efrei.se.emapp.api.model.exception.EmptyResultException;
import fr.efrei.se.emapp.api.security.Role;
import fr.efrei.se.emapp.api.security.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static fr.efrei.se.emapp.api.resources.ResourceHelper.checkCredentials;
import static fr.efrei.se.emapp.api.resources.ResourceHelper.getLink;


@Path("credentials")
public class CredentialResource {
    private Gson gson = new Gson();

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response tryAuth(@FormParam("login")String login, @FormParam("password")String password) throws DBComException {
        try {
            return Response.ok(checkCredentials(login, password)).build();
        } catch (EmptyParameterException | EmptyResultException e) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @GET
    @Path("/{id}")
    @Secured({Role.USER, Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id")int id) throws DBComException {
        Credential c = new Credential(getLink(), id);
        c.read();
        return Response.ok(gson.toJson(ResourceHelper.convertCredential(c))).build();
    }
}
