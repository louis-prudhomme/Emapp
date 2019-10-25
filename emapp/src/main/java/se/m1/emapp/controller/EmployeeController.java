package se.m1.emapp.controller;

import se.m1.emapp.model.business.Employee;
import se.m1.emapp.model.core.exception.DBComException;

import javax.servlet.ServletException;
import java.io.IOException;

import static se.m1.emapp.utils.Constants.*;

public class EmployeeController implements IController {
    private ServletContext servletContext;

    EmployeeController(ServletContext context) {
        this.servletContext = context;
    }

    @Override
    public String handle(WordOfPower action) throws ServletException, IOException {
        switch (action) {
            //todo when choosing add or detail on the welcome.jsp page
            case ADD:
                servletContext.getSession().setAttribute("employeeChecked", new Employee(servletContext.getDbLink(), 0));
                return JSP_ADD;
            case DETAILS:
                try {
                    int id = Integer.parseInt(servletContext.getRequest().getParameter("check"));
                    Employee employee = new Employee(servletContext.getDbLink(), id);
                    employee.read();
                    servletContext.getSession().setAttribute("employeeChecked", employee);
                } catch (DBComException e) {
                    //todo send error
                    TheOneServlet.sendError(servletContext.getRequest(), servletContext.getResponse(), e);
                } catch (NumberFormatException e) {
                    servletContext.getRequest().setAttribute("errCheck", ERR_CHECK);
                }
                return JSP_ADD;
            case DELETE:
                try {
                    int id = Integer.parseInt(servletContext.getRequest().getParameter("check"));
                    new Employee(servletContext.getDbLink(), id).delete();
                } catch (DBComException e) {
                    //todo send error
                    TheOneServlet.sendError(servletContext.getRequest(), servletContext.getResponse(), e);
                } catch (NumberFormatException e) {
                    servletContext.getRequest().setAttribute("errCheck", ERR_CHECK);
                }
                return JSP_WELCOME_PAGE;
            case COMMIT:
                int id = servletContext.getSession().getAttribute("employeeChecked") != null ? ((Employee)servletContext.getSession().getAttribute("employeeChecked")).getId() : 0;
                Employee employee = new Employee(servletContext.getDbLink(), id, servletContext.getRequest().getParameter("inputFirstName"),
                         servletContext.getRequest().getParameter("inputLastName"),  servletContext.getRequest().getParameter("inputHomePhone"),
                         servletContext.getRequest().getParameter("inputMobilePhone"),  servletContext.getRequest().getParameter("inputWorkPhone"),
                         servletContext.getRequest().getParameter("inputAddress"),  servletContext.getRequest().getParameter("inputPostalCode"),
                         servletContext.getRequest().getParameter("inputCity"),  servletContext.getRequest().getParameter("inputEmail"), false);
                try {
                    if(id != 0) {
                        employee.update();
                    } else {
                        employee.create();
                    }
                } catch (DBComException e) {
                    //todo send error
                    TheOneServlet.sendError(servletContext.getRequest(), servletContext.getResponse(), e);
                }
                return JSP_WELCOME_PAGE;
            case CANCEL:
                servletContext.getSession().removeAttribute("employeeChecked");
            default:
                return JSP_WELCOME_PAGE;
        }
    }
}
