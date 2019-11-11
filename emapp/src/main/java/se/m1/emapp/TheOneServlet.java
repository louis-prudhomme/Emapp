/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.m1.emapp;

import se.m1.emapp.controller.ControllerFactory;
import se.m1.emapp.controller.IController;
import se.m1.emapp.controller.StateOfPower;
import se.m1.emapp.controller.WordOfPower;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.http.HttpSession;
import se.m1.emapp.model.core.JPAManager;

import static se.m1.emapp.utils.Constants.*;


public class TheOneServlet extends HttpServlet {

    private String nextPage;
    private IController controller;
    private StateOfPower state;
    
    @EJB
    private JPAManager jpa;
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
     private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //parses the action parameter into a WordOfPower enum
        
        request.setAttribute("action", WordOfPower.fromString(request.getParameter("action")));

        if (request.getSession().getAttribute("user") == null || request.getAttribute("action") == WordOfPower.LOGOUT) {
            state = StateOfPower.SESSION;
        } else {
            state = StateOfPower.EMPLOYEE;
        }

        //gets controller
        controller = ControllerFactory.dispatch(request, response,jpa, state);
        if(controller == null) {
            
            nextPage = JSP_ERROR_PAGE;
        } else {
            nextPage = controller.handle((WordOfPower)request.getAttribute("action"));
        }

        //if the next page is welcome.jsp we load the list containing all the employees
        //this is made to avoid repeating it over and over in employeeController
        if(nextPage.equals(JSP_WELCOME_PAGE)) {
                request.setAttribute("empList", jpa.getAll());          
        }
        if(request.getParameter("action") != null && request.getParameter("action").equalsIgnoreCase(WordOfPower.COMMIT.name())) {
            response.sendRedirect("se.m1.emapp.controller");
        } else {
            request.getRequestDispatcher(nextPage).forward(request, response);
        }
        
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
