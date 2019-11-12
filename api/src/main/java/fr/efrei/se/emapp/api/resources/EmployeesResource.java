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
 * handles all employees-related requests
 */
@Path("employees")
public class EmployeesResource {
    private Gson gson = new Gson();

    @EJB
    private JPAManager jpaManager = new JPAManager();

    /**
     * handles a general get request by returning all the employees in database
     * @return json-serialized list of all the employees
     */
    @GET
    @Secured({Role.USER, Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        ArrayList<EmployeeTranscript> transcripts = new ArrayList<>();
        try {
            jpaManager.getAll().forEach(e -> transcripts.add(ResourceHelper.convertEmployee(e)));
            return Response.ok(gson.toJson(transcripts)).build();
        } catch (EmptyResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * handles specialized get request and returns the one matching employee
     * @param id of the wanted employee
     * @return json-serialized employee
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
     * handles delete requests on a particular id
     * @param id of the employee one wants to delete
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
     * handles put requests
     * semanticaly, should update employees
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
     * handles post requests
     * semanticaly, should create employees
     * @return http 200 in all cases //todo faire un truc stylé pour la valeur de retour
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
