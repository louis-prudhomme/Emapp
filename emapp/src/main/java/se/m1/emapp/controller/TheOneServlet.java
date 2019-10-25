package se.m1.emapp.controller;

import se.m1.emapp.model.business.AppDbHelper;
import se.m1.emapp.model.business.Credential;
import se.m1.emapp.model.business.Employee;
import se.m1.emapp.model.core.DBLink;
import se.m1.emapp.model.core.DBObject;
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
    private String action;
    private AppDbHelper helper;
    private Credential user;

    private ServletContext servletContext;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        this.properties = new Properties();
        properties.load(getServletContext().getResourceAsStream(PROP_FILE_PATH));

        try {
            this.dbLink = DBLink.getNewInstance(properties.getProperty("dbUrl"), properties.getProperty("dbUser"), properties.getProperty("dbPwd"));
            this.dbLink.connect();
            request.setAttribute("dbLink", dbLink);
        } catch (DBLException e) {
            sendError(request, response, e);
        }

        request.setAttribute("action", WordOfPower.fromString(request.getParameter("action")));

        servletContext = new ServletContext(request, response);
        IController controller = ControllerFactory.dispatch(servletContext, request.getSession().getAttribute("user") == null || (WordOfPower)request.getAttribute("action") == WordOfPower.LOGOUT ? StateOfPower.SESSION : StateOfPower.EMPLOYEE);
        String page = controller.handle((WordOfPower)request.getAttribute("action"));

        if(page.equals(JSP_WELCOME_PAGE)) {
            try {
                request.setAttribute("empList", Employee.selectAll((DBLink)request.getAttribute("dbLink"), Employee.class));
            } catch (DBComException e) {
                sendError(request, response, e);
            }
        }
        request.getRequestDispatcher(page).forward(request, response);
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

    public static void sendError(HttpServletRequest request, HttpServletResponse response, Exception errorText) throws ServletException, IOException {
        request.setAttribute("errorMessage", errorText.getMessage());
        request.setAttribute("firstDigit", 5);
        request.setAttribute("secondDigit", 0);
        request.setAttribute("thirdDigit", 0);
        request.getRequestDispatcher(JSP_ERROR_PAGE).forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
