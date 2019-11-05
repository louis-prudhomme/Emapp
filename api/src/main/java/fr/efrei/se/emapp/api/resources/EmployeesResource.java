package fr.efrei.se.emapp.api.resources;

import com.google.gson.Gson;
import fr.efrei.se.emapp.api.model.business.Employee;
import fr.efrei.se.emapp.api.model.core.exception.DBComException;
import fr.efrei.se.emapp.api.security.Role;
import fr.efrei.se.emapp.api.security.Secured;
import fr.efrei.se.emapp.common.model.EmployeeTranscript;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

import static fr.efrei.se.emapp.api.resources.ResourceHelper.getLink;

@Path("employees")
public class EmployeesResource {
    private Gson gson = new Gson();

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Secured({Role.USER, Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() throws DBComException {
        ArrayList<EmployeeTranscript> transcripts = new ArrayList<>();
        Employee.selectAll(getLink(), Employee.class).forEach(e -> transcripts.add(ResourceHelper.convertEmployee(e)));
        return gson.toJson(transcripts);
    }

    @GET
    @Path("/{id}")
    @Secured({Role.USER, Role.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public String getOne(@PathParam("id")int id) throws DBComException {
        Employee e = new Employee(getLink(), id);
        e.read();
        return gson.toJson(ResourceHelper.convertEmployee(e));
    }

    @DELETE
    @Path("/{id}")
    @Secured(Role.ADMIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteOne(@PathParam("id")int id) throws DBComException {
        Employee e = new Employee(getLink(), id);
        e.delete();
        //todo test it ?
        return gson.toJson(true);
    }

    @PUT
    @Secured(Role.ADMIN)
    @Produces(MediaType.APPLICATION_JSON)
    public String putOne() throws DBComException {
        System.out.println("");
        //todo
        return gson.toJson(true);
    }
}
