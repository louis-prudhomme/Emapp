package fr.efrei.se.emapp.api;

import com.google.gson.Gson;
import fr.efrei.se.emapp.api.model.business.Employee;
import fr.efrei.se.emapp.api.model.core.DBLink;
import fr.efrei.se.emapp.api.model.core.exception.DBComException;
import fr.efrei.se.emapp.common.model.EmployeeTranscript;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

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
    @Produces(MediaType.APPLICATION_JSON)
    public String getAll() throws DBComException {
        ArrayList<EmployeeTranscript> transcripts = new ArrayList<>();
        Employee.selectAll(getLink(), Employee.class).forEach(e -> transcripts.add(EmployeeHelper.convertEmployee(e)));
        return gson.toJson(transcripts);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOne(@PathParam("id")int id) throws DBComException {
        Employee e = new Employee(getLink(), id);
        e.read();
        return gson.toJson(EmployeeHelper.convertEmployee(e));
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteOne(@PathParam("id")int id) throws DBComException {
        Employee e = new Employee(getLink(), id);
        e.delete();
        //todo test it ?
        return gson.toJson(true);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String putOne() throws DBComException {
        System.out.println("");
        //todo
        return gson.toJson(true);
    }

    private DBLink getLink() throws DBComException {
        DBLink dbLink = DBLink.getNewInstance("jdbc:mysql://localhost:3306/JEEPRJ", "test", "password");
        dbLink.connect();
        return dbLink;
    }
}
