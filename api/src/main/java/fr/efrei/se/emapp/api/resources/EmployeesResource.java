package fr.efrei.se.emapp.api.resources;

import com.google.gson.Gson;
import fr.efrei.se.emapp.api.model.business.Employee;
import fr.efrei.se.emapp.api.model.core.JPAManager;
import fr.efrei.se.emapp.api.model.exception.EmptyParameterException;
import fr.efrei.se.emapp.api.model.exception.EmptyResultException;
import fr.efrei.se.emapp.common.security.Role;
import fr.efrei.se.emapp.api.security.Secured;
import fr.efrei.se.emapp.common.model.EmployeeTranscript;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static fr.efrei.se.emapp.api.resources.ResourceHelper.*;

/**
 * Handles all the {@link Employee}-related requests
 * Makes the CRUD
 */
@Path("employees")
public class EmployeesResource {
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
     * Handles an {@link HttpMethod} GET request without further parameters
     * Returns all the employees in database
     * @return JSON-serialized list of all {@link EmployeeTranscript}
     */
    @GET
    @Secured({Role.USER, Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        ArrayList<EmployeeTranscript> transcripts = new ArrayList<>();
        try {
            //conversion between {@link Employee} and {@link EmployeeTranscript}
            jpaManager.getAll().forEach(e -> transcripts.add(ResourceHelper.convertEmployee(e)));
            return Response.ok(gson.toJson(transcripts)).build();
        } catch (EmptyResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Handles an {@link HttpMethod} GET request with an URL-specified ID
     * Returns the one matching {@link Employee}
     * @param id of the desired {@link Employee}
     * @return a JSON-serialized {@link EmployeeTranscript} representing the desired {@link Employee} if it exists
     */
    @GET
    @Path("/{id}")
    @Secured({Role.USER, Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id")int id) {
        try {
            return Response.ok(gson.toJson(convertEmployee(jpaManager.read(id)))).build();
        } catch (EmptyParameterException e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        } catch (EmptyResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Handles an {@link HttpMethod} DELETE request with an URL-specified ID
     * Deletes the one matching {@link Employee}
     * @param id of the desired {@link Employee}
     * @return http 200 all cases //todo faire un truc stylé pour la valeur de retour
     */
    @DELETE
    @Path("/{id}")
    @Secured(Role.ADMIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOne(@PathParam("id")int id) {
        try {
            jpaManager.removeEmployee(jpaManager.read(id));
            return Response.ok().build();
        } catch (EmptyParameterException e) {
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
        } catch (EmptyResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Handles an {@link HttpMethod} PUT request with an URL-specified ID
     * Updates the one matching {@link Employee}
     * @param employee {@link EmployeeTranscript} representation of the {@link Employee} to update
     * @return http 200 all cases //todo faire un truc stylé pour la valeur de retour
     */
    @PUT
    @Path("/{id}")
    @Secured(Role.ADMIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response putOne(@FormParam("employee")EmployeeTranscript employee) {
        jpaManager.modifyEmployee(convertEmployeeTranscript(employee));
        return Response.ok().build();
    }

    /**
     * Handles an {@link HttpMethod} POST request with an URL-specified ID
     * Creates the corresponding {@link Employee}
     * @param employee {@link EmployeeTranscript} representation of the {@link Employee} to create
     * @return http 200 all cases //todo faire un truc stylé pour la valeur de retour
     */
    @POST
    @Secured(Role.ADMIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postOne(@FormParam("employee")EmployeeTranscript employee) {
        jpaManager.createEmployee(convertEmployeeTranscript(employee));
        return Response.ok().build();
    }
}
