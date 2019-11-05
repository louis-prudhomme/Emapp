package fr.efrei.se.emapp.web.controller;

import fr.efrei.se.emapp.common.model.EmployeeTranscript;
import fr.efrei.se.emapp.web.TheOneServlet;
import fr.efrei.se.emapp.web.utils.HttpMethod;
import fr.efrei.se.emapp.web.utils.HttpRequestHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static fr.efrei.se.emapp.web.utils.Constants.*;
import static fr.efrei.se.emapp.web.utils.HttpMethod.DELETE;
import static fr.efrei.se.emapp.web.utils.HttpMethod.PUT;

public class EmployeeController implements IController {
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * session and dblink are shortcuts to avoid cluttering the code with calls through request
     */
    private HttpSession session;

    /**
     * default constructor
     * @param request http servlet request
     * @param response http servlet response
     */
    EmployeeController(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    /**
     * allows the controller to handle a request
     * it can handle employee-manipulation requests, including
     * add, details, delete, commit, cancer and the default case
     * @param action user's request
     * @return a string representing the view to serve
     * @throws ServletException unexpected
     * @throws IOException unexpected
     */
    @Override
    public String handle(WordOfPower action) throws ServletException, IOException {
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
            default:
                return JSP_WELCOME_PAGE;
        }
    }

    /**
     * handles the commit request
     * @return next page
     */
    private String commit() {
        //we check if the employee is either a fake one (id = 0) or not
        //if fake, then it is a create request ; otherwise, this is an update
        int id = session.getAttribute("employeeChecked") != null ? ((EmployeeTranscript)session.getAttribute("employeeChecked")).getId() : 0;
        EmployeeTranscript employee = new EmployeeTranscript();
        employee.setId(id);
        employee.setFirstName(request.getParameter("inputFirstName"));
        employee.setFirstName(request.getParameter("inputLastName"));
        //request.getParameter("inputLastName"),  request.getParameter("inputHomePhone"), request.getParameter("inputMobilePhone"),  request.getParameter("inputWorkPhone"), request.getParameter("inputAddress"),  request.getParameter("inputPostalCode"), request.getParameter("inputCity"),  request.getParameter("inputEmail"), false);
        try {
            HttpRequestHelper.put(EMPLOYEES_URI, "haddock", employee);
        } catch (IOException e) {
            TheOneServlet.setErrorMessage(request, e, DB_COM_ERROR_CODE);
            return JSP_ERROR_PAGE;
        }
        return JSP_WELCOME_PAGE;
    }

    /**
     * handles the details request
     * @return next page
     */
    private String details() {
        try {
            int id = Integer.parseInt(request.getParameter("check"));
            EmployeeTranscript employee = HttpRequestHelper.get(EMPLOYEES_URI + "/" + id, "haddock", EmployeeTranscript.class);
            session.setAttribute("employeeChecked", employee);
        } catch (IOException e) {
            TheOneServlet.setErrorMessage(request, e, DB_COM_ERROR_CODE);
            return JSP_ERROR_PAGE;
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
     */
    private String delete() {
        try {
            int id = Integer.parseInt(request.getParameter("check"));
            HttpRequestHelper.request(DELETE, EMPLOYEES_URI + "/" + id, "haddock");
        } catch (IOException e) {
            TheOneServlet.setErrorMessage(request, e, DB_COM_ERROR_CODE);
            return JSP_ERROR_PAGE;
        } catch (NumberFormatException e) {
            //fires if there's an a error with the id
            request.setAttribute("errCheck", ERR_CHECK);
        }
        return JSP_WELCOME_PAGE;
    }
}
