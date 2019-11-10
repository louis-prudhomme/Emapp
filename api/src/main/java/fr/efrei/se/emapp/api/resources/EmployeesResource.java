package fr.efrei.se.emapp.api.resources;

import com.google.gson.Gson;
import fr.efrei.se.emapp.api.model.business.Employee;
import fr.efrei.se.emapp.api.model.core.exception.DBComException;
import fr.efrei.se.emapp.common.security.Role;
import fr.efrei.se.emapp.api.security.Secured;
import fr.efrei.se.emapp.common.model.EmployeeTranscript;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static fr.efrei.se.emapp.api.resources.ResourceHelper.convertEmployeeTranscript;
import static fr.efrei.se.emapp.api.resources.ResourceHelper.getLink;

/**
 * handles all employees-related requests
 */
@Path("employees")
public class EmployeesResource {
    private Gson gson = new Gson();

    /**
     * handles a general get request by returning all the employees in database
     * @return json-serialized list of all the employees
     * @throws DBComException sa va murir mdr
     */
    @GET
    @Secured({Role.USER, Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() throws DBComException {
        ArrayList<EmployeeTranscript> transcripts = new ArrayList<>();
        Employee.selectAll(getLink(), Employee.class).forEach(e -> transcripts.add(ResourceHelper.convertEmployee(e)));
        return Response.ok(gson.toJson(transcripts)).build();
    }

    /**
     * handles specialized get request and returns the one matching employee
     * @param id of the wanted employee
     * @return json-serialized employee
     * @throws DBComException sa va murir mdr
     */
    @GET
    @Path("/{id}")
    @Secured({Role.USER, Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne(@PathParam("id")int id) throws DBComException {
        Employee e = new Employee(getLink(), id);
        e.read();
        //todo add error when empty
        return Response.ok(gson.toJson(ResourceHelper.convertEmployee(e))).build();
    }

    /**
     * handles delete requests on a particular id
     * @param id of the employee one wants to delete
     * @return http 200 all cases //todo faire un truc stylé pour la valeur de retour
     * @throws DBComException sa va murir mdr
     */
    @DELETE
    @Path("/{id}")
    @Secured(Role.ADMIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOne(@PathParam("id")int id) throws DBComException {
        Employee e = new Employee(getLink(), id);
        e.delete();
        return Response.ok().build();

    }

    /**
     * handles put requests
     * semanticaly, should update employees
     * @return http 200 all cases //todo faire un truc stylé pour la valeur de retour
     * @throws DBComException sa va murir mdr
     */
    @PUT
    @Path("/{id}")
    @Secured(Role.ADMIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response putOne(@PathParam("id")int id, @FormParam("employee")EmployeeTranscript employee)  throws DBComException {
        convertEmployeeTranscript(employee).update();
        return Response.ok().build();
    }

    /**
     * handles post requests
     * semanticaly, should create employees
     * @return http 200 in all cases //todo faire un truc stylé pour la valeur de retour
     * @throws DBComException sa va murir mdr
     */
    @POST
    @Secured(Role.ADMIN)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response postOne(@FormParam("employee")EmployeeTranscript employee) throws DBComException {
        convertEmployeeTranscript(employee).create();
        return Response.ok().build();
    }
}
