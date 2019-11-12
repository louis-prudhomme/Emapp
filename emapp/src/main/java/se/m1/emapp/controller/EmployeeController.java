package se.m1.emapp.controller;

import se.m1.emapp.model.business.Employee;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ejb.EJBException;
import se.m1.emapp.TheOneServlet;
import se.m1.emapp.model.core.JPAManager;

import static se.m1.emapp.utils.Constants.*;

public class EmployeeController implements IController {
    /**
     * Servlet’s context
     */
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Persistence manager
     */
    private JPAManager jpa;
    
    /**
     * Session is a shortcut to avoid cluttering the code with calls through request
     */
    private HttpSession session;

    /**
     * default constructor
     * @param request http servlet request
     * @param response http servlet response
     */
    EmployeeController(HttpServletRequest request, HttpServletResponse response, JPAManager jpa) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.jpa = jpa;
        
    }

    /**
     * allows the controller to handle a request
     * it can handle employee-manipulation requests, including
     * add, details, delete, commit, cancer and the default case
     * @param action user's request
     * @return a string representing the view to serve
     * @throws EJBException
     */
    @Override
    public String handle(WordOfPower action) throws EJBException {
        switch (action) {
            case ADD:
                //add.jsp needs an employee ; we create this one to fake it
                session.setAttribute("employeeChecked", new Employee(0));               
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
     * @throws EJBException
     */
    private String commit() throws EJBException {
        //we check if the employee is either a fake one (id = 0) or not
        //if fake, then it is a create request ; otherwise, this is an update
        int id = session.getAttribute("employeeChecked") != null ? ((Employee)session.getAttribute("employeeChecked")).getId() : 0;
        Employee employee = new Employee(id, request.getParameter("inputFirstName"),
                request.getParameter("inputLastName"),  request.getParameter("inputHomePhone"),
                request.getParameter("inputMobilePhone"),  request.getParameter("inputWorkPhone"),
                request.getParameter("inputAddress"),  request.getParameter("inputPostalCode"),request.getParameter("inputCity"), request.getParameter("inputEmail"));
            if(id != 0) { 
                boolean verif = jpa.modifyEmployee(employee);
                if(!verif) {
                    TheOneServlet.setErrorMessage(request, "Employee not found", USER_WRONG_PAGE_CODE);
                    request.setAttribute("commit", true);
                    return JSP_ERROR_PAGE;
                }
            } else {
                jpa.createEmployee(employee);
            }
        session.removeAttribute("employeeChecked");
        return JSP_WELCOME_PAGE;
    }

    /**
     * handles the details request
     * @return next page
     * @throws EJBException
     */
    private String details() throws EJBException {
        try {
            int id = Integer.parseInt(request.getParameter("check"));
            Employee employee = jpa.read(id);
            if(employee==null) {
                TheOneServlet.setErrorMessage(request, "Employee not found", USER_WRONG_PAGE_CODE);
                request.setAttribute("commit", true);
                return JSP_ERROR_PAGE;
            }
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
     * @throws EJBException
     */
    private String delete() throws EJBException {
        try {
            int id = Integer.parseInt(request.getParameter("check"));
            boolean check = jpa.removeEmployee(new Employee(id));
            if(!check) {
                TheOneServlet.setErrorMessage(request, "Employee not found", USER_WRONG_PAGE_CODE);
                request.setAttribute("commit", true);
                return JSP_ERROR_PAGE;
            }
        } catch (NumberFormatException e) {
            //fires if there's an a error with the id
            request.setAttribute("errCheck", ERR_CHECK);
        }
        return JSP_WELCOME_PAGE;
    }
}
