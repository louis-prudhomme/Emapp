package fr.efrei.se.emapp.api;

import fr.efrei.se.emapp.api.model.business.Employee;
import fr.efrei.se.emapp.api.model.core.DBLink;
import fr.efrei.se.emapp.api.model.core.exception.DBComException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("employee")
public class EmployeeResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() throws DBComException {
        List<Employee> employees = Employee.selectAll(getLink(), Employee.class);
        StringBuilder res = new StringBuilder();
        for (Employee e : employees) {
            res.append(e.getId()).append("\t");
            res.append(e.getFirstName()).append("\t");
            res.append(e.getLastName()).append("\n");
        }
        return res.toString();
    }

    private DBLink getLink() throws DBComException {
        DBLink dbLink = DBLink.getNewInstance("jdbc:mysql://localhost:3306/JEEPRJ", "test", "password");
        dbLink.connect();
        return dbLink;
    }
}
