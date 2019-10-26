package se.m1.emapp;

import se.m1.emapp.controller.ControllerFactory;
import se.m1.emapp.controller.IController;
import se.m1.emapp.controller.StateOfPower;
import se.m1.emapp.controller.WordOfPower;
import se.m1.emapp.model.business.Employee;
import se.m1.emapp.model.core.DBLink;
import se.m1.emapp.model.core.exception.DBComException;
import se.m1.emapp.model.core.exception.dbLink.DBLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

import static se.m1.emapp.utils.Constants.*;

public class TheOneServlet extends HttpServlet {
    private Properties properties;
    private DBLink dbLink;
    private String nextPage;
    private IController controller;
    private StateOfPower state;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        //loads the properties
        this.properties = new Properties();
        properties.load(getServletContext().getResourceAsStream(PROP_FILE_PATH));

        //loads the link to the database
        try {
            this.dbLink = DBLink.getNewInstance(properties.getProperty("dbUrl"), properties.getProperty("dbUser"), properties.getProperty("dbPwd"));
            this.dbLink.connect();
            request.setAttribute("dbLink", dbLink);
        } catch (DBLException e) {
            TheOneServlet.setErrorMessage(request, e, DB_COM_ERROR_CODE);
            request.getRequestDispatcher(JSP_ERROR_PAGE).forward(request, response);
        }

        //parses the action parameter into a WordOfPower enum
        request.setAttribute("action", WordOfPower.fromString(request.getParameter("action")));

        if (request.getSession().getAttribute("user") == null || request.getAttribute("action") == WordOfPower.LOGOUT) {
            state = StateOfPower.SESSION;
        } else {
            state = StateOfPower.EMPLOYEE;
        }

        //gets controller
        controller = ControllerFactory.dispatch(request, response, state);
        if(controller == null) {
            nextPage = JSP_ERROR_PAGE;
        } else {
            nextPage = controller.handle((WordOfPower)request.getAttribute("action"));
        }

        //if the next page is welcome.jsp we load the list containing all the employees
        //this is made to avoid repeating it over and over in employeeController
        if(nextPage.equals(JSP_WELCOME_PAGE)) {
            try {
                request.setAttribute("empList", Employee.selectAll((DBLink)request.getAttribute("dbLink"), Employee.class));
            } catch (DBComException e) {
                TheOneServlet.setErrorMessage(request, e, DB_COM_ERROR_CODE);
                nextPage = JSP_ERROR_PAGE;
            }
        }
        request.getRequestDispatcher(nextPage).forward(request, response);
    }

    /**
     * sets an error (its message and code) in the request
     * @param request user's request and storage place
     * @param error exception raised during application execution
     * @param errorCode 404, 50x…
     */
    public static void setErrorMessage(HttpServletRequest request, Exception error, String errorCode) {
        request.setAttribute("errorMessage", error.getMessage());
        request.setAttribute("firstDigit", errorCode.charAt(0));
        request.setAttribute("secondDigit",  errorCode.charAt(1));
        request.setAttribute("thirdDigit",  errorCode.charAt(2));
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
