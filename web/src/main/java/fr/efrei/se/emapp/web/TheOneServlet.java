package fr.efrei.se.emapp.web;

import fr.efrei.se.emapp.common.model.CredentialTranscript;
import fr.efrei.se.emapp.common.model.EmployeeTranscript;
import fr.efrei.se.emapp.common.security.Role;
import fr.efrei.se.emapp.web.controller.ControllerFactory;
import fr.efrei.se.emapp.web.controller.IController;
import fr.efrei.se.emapp.web.controller.StateOfPower;
import fr.efrei.se.emapp.web.controller.WordOfPower;
import fr.efrei.se.emapp.web.security.RoleMatcher;
import fr.efrei.se.emapp.web.utils.HttpRequestHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static fr.efrei.se.emapp.common.utils.Constants.TOKEN_FILE;
import static fr.efrei.se.emapp.web.utils.Constants.*;

public class TheOneServlet extends HttpServlet {
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

        //parses the action parameter into a WordOfPower enum
        request.setAttribute("action", WordOfPower.fromString(request.getParameter("action")));

        if (request.getSession().getAttribute("user") == null || request.getAttribute("action") == WordOfPower.LOGOUT) {
            state = StateOfPower.SESSION;
        } else {
            state = StateOfPower.EMPLOYEE;
        }

        //gets controller
        controller = ControllerFactory.dispatch(request, state);
        if(controller == null) {
            nextPage = JSP_ERROR_PAGE;
        } else {
            nextPage = controller.handle((WordOfPower)request.getAttribute("action"));
        }

        //if the next page is welcome.jsp we load the list containing all the employees
        //this is made to avoid repeating it over and over in employeeController
        if(nextPage.equals(JSP_WELCOME_PAGE)) {
            try {
                request.setAttribute("empList", HttpRequestHelper.getAll(EMPLOYEES_URI, getSessionToken(request.getSession()), EmployeeTranscript.class));
            } catch (Exception e) {
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

    /**
     * shortcut to get the current session΅s user’s role
     * @param session current session
     * @return current session’s user’s role
     */
    private static Role getRole(HttpSession session) {
        return ((CredentialTranscript)session.getAttribute("user")).isAdmin() ? Role.ADMIN : Role.USER;
    }

    /**
     * shortcut to get the current session’s token
     * @param session target
     * @return session’s user’s token
     * @throws IOException if the token property file cannot be read
     */
    public static String getSessionToken(HttpSession session) throws IOException {
        return new RoleMatcher(TOKEN_FILE).getCorrespondingToken(getRole(session));
    }
}
