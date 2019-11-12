package fr.efrei.se.emapp.web.controller;

import fr.efrei.se.emapp.common.model.EmployeeTranscript;
import fr.efrei.se.emapp.web.TheOneServlet;
import fr.efrei.se.emapp.web.utils.HttpRequestHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static fr.efrei.se.emapp.web.utils.Constants.*;
import static fr.efrei.se.emapp.web.utils.HttpMethod.DELETE;

/**
 * controls everything employee-manipulation related
 */
public class EmployeeController implements IController {
    /**
     * incoming request
     */
    private HttpServletRequest request;
    /**
     * session is a shortcut to avoid cluttering the code with calls through request
     */
    private HttpSession session;

    /**
     * default constructor
     * @param request http servlet request
     */
    EmployeeController(HttpServletRequest request) {
        this.request = request;
        this.session = request.getSession();
    }

    /**
     * allows the controller to handle a request
     * it can handle employee-manipulation requests, including
     * add, details, delete, commit, cancer and the default case
     * @param action user's request
     * @return a string representing the view to serve
     * @throws IOException in case of DB comm error
     */
    @Override
    public String handle(WordOfPower action) throws IOException {
        switch (action) {
            case ADD:
                //add.jsp needs an employee ; we create this one to fake it
                session.setAttribute("employeeChecked", new EmployeeTranscript());
                return JSP_ADD;
            case DETAILS:
                return details();
            case DELETE:
                return delete();
            case COMMIT:
                return commit();
            case CANCEL:
                session.removeAttribute("employeeChecked");
            case HOME:
                return JSP_HOME_PAGE;
            default:
                return JSP_WELCOME_PAGE;
        }
    }

    /**
     * handles the commit request
     * @return next page
     * @throws IOException in case of DB comm error
     */
    private String commit() throws IOException {
        //we check if the employee is either a fake one (id = 0) or not
        //if fake, then it is a create request ; otherwise, this is an update
        int id = session.getAttribute("employeeChecked") != null ? ((EmployeeTranscript)session.getAttribute("employeeChecked")).getId() : 0;

        EmployeeTranscript employee = extractEmployeeFromRequest(request, id);
        if (id == 0) {
            HttpRequestHelper.post(EMPLOYEES_URI, TheOneServlet.getSessionToken(session), "employee", employee);
        } else {
            HttpRequestHelper.put(EMPLOYEES_URI + "/" + id, TheOneServlet.getSessionToken(session), "employee", employee);
        }
        return JSP_WELCOME_PAGE;
    }

    /**
     * handles the details request
     * @return next page
     * @throws IOException in case of DB comm error
     */
    private String details() throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("check"));
            EmployeeTranscript employee = HttpRequestHelper.get(EMPLOYEES_URI + "/" + id,
                    TheOneServlet.getSessionToken(session), EmployeeTranscript.class);
            session.setAttribute("employeeChecked", employee);
        } catch (NumberFormatException e) {
            //fires if there's an a error with the id
            request.setAttribute("errCheck", ERR_CHECK);
            return JSP_WELCOME_PAGE;
        }
        return JSP_ADD;
    }

    /**
     * handles the delete request
     * @return next page
     * @throws IOException in case of DB comm error
     */
    private String delete() throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("check"));
            HttpRequestHelper.request(DELETE, EMPLOYEES_URI + "/" + id,
                    TheOneServlet.getSessionToken(session));
        } catch (NumberFormatException e) {
            //fires if there's an a error with the id
            request.setAttribute("errCheck", ERR_CHECK);
        }
        return JSP_WELCOME_PAGE;
    }

    private EmployeeTranscript extractEmployeeFromRequest(HttpServletRequest request, int id) {
        EmployeeTranscript employee = new EmployeeTranscript();
        employee.setId(id);
        employee.setFirstName(request.getParameter("inputFirstName"));
        employee.setLastName(request.getParameter("inputLastName"));
        employee.setMobilePhone(request.getParameter("inputMobilePhone"));
        employee.setHomePhone(request.getParameter("inputHomePhone"));
        employee.setWorkPhone(request.getParameter("inputWorkPhone"));
        employee.setAddress(request.getParameter("inputAddress"));
        employee.setCity(request.getParameter("inputCity"));
        employee.setPostalCode(request.getParameter("inputPostalCode"));
        employee.setEmail(request.getParameter("inputEmail"));

        return employee;
    }
}
