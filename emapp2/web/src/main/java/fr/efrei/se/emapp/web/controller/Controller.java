package fr.efrei.se.emapp.web.controller;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static fr.efrei.se.emapp.web.utils.Constants.*;

public class Controller extends HttpServlet {
    private Properties properties;
    private String action;
    private HttpSession session;
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

        if (request.getParameter("action") == null) {
            request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
        } else {
            action = request.getParameter("action");
            session = request.getSession();
            switch (action) {
                case "Login":
                    session.setAttribute("empList", DBObject.selectAll(dbLink, Employee.class));
                    session.setAttribute("user", user);

                    request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
                    break;
                case "Delete":
                    new Employee(dbLink, id).delete();
                    session.setAttribute("empList", DBObject.selectAll(dbLink, Employee.class));
                    break;
                case "Details":
                    if(request.getParameter("check") == null) {
                        request.setAttribute("errCheck", ERR_CHECK);
                        request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
                    } else {
                        int idD = Integer.parseInt(request.getParameter("check"));
                        try {
                            Employee employee = new Employee(dbLink, idD);
                            employee.read();
                            session.setAttribute("employeeChecked", employee);
                            request.getRequestDispatcher(JSP_ADD).forward(request, response);
                        } catch (DatabaseCommunicationException e) {
                            sendError(request, response, e);
                        }
                    }
                    break;
                case "Add":
                    session.setAttribute("employeeChecked", new Employee(dbLink, 0));
                    request.getRequestDispatcher(JSP_ADD).forward(request, response);
                    break;
                case "Save":
                    if (((Employee)session.getAttribute("employeeChecked")).getId() != 0) {
                        Employee employee = new Employee(dbLink, ((Employee)session.getAttribute("employeeChecked")).getId(), request.getParameter("inputFirstName"),
                                request.getParameter("inputLastName"), request.getParameter("inputHomePhone"),
                                request.getParameter("inputMobilePhone"), request.getParameter("inputWorkPhone"),
                                request.getParameter("inputAddress"), request.getParameter("inputPostalCode"),
                                request.getParameter("inputCity"), request.getParameter("inputEmail"), false);
                        try {
                            employee.update();
                            session.setAttribute("empList", DBObject.selectAll(dbLink, Employee.class));
                            session.removeAttribute("employeeChecked");
                        } catch (DatabaseCommunicationException e) {
                            sendError(request, response, e);
                        }
                    } else {
                        Employee employee = new Employee(dbLink, request.getParameter("inputFirstName"),
                                request.getParameter("inputLastName"), request.getParameter("inputHomePhone"),
                                request.getParameter("inputMobilePhone"), request.getParameter("inputWorkPhone"),
                                request.getParameter("inputAddress"), request.getParameter("inputPostalCode"),
                                request.getParameter("inputCity"), request.getParameter("inputEmail"), false);
                        try {
                            employee.create();
                        } catch (DatabaseCommunicationException e) {
                            sendError(request, response, e);
                        }
                    }
                    try {
                        session.setAttribute("empList", DBObject.selectAll(dbLink, Employee.class));
                    } catch (DatabaseCommunicationException e) {
                        sendError(request, response, e);
                    }
                    request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
                    break;
                case "Cancel":
                    if (session.getAttribute("employeeChecked") != null) {
                        session.removeAttribute("employeeChecked");
                    }
                    try {
                        session.setAttribute("empList", DBObject.selectAll(dbLink, Employee.class));
                    } catch (DatabaseCommunicationException e) {
                        sendError(request, response, e);
                    }

                    request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
                case "LogOut":
                    request.getRequestDispatcher(JSP_GOODBYE_PAGE).forward(request, response);
                default:
                    request.getRequestDispatcher(JSP_HOME_PAGE).forward(request, response);
            }
        }
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

    private void sendError(HttpServletRequest request, HttpServletResponse response, Exception errorText) throws ServletException, IOException {
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
