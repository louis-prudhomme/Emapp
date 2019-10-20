package se.m1.emapp.controller;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static se.m1.emapp.utils.Constants.*;

import se.m1.emapp.model.business.AppDbHelper;
import se.m1.emapp.model.business.Employee;
import se.m1.emapp.model.core.DBLink;
import se.m1.emapp.model.core.DBObject;


public class Controller extends HttpServlet {
    private Properties properties;
    private DBLink dbLink;
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (request.getParameter("action") == null) {
            request.getRequestDispatcher(JSP_HOME_PAGE).forward(request, response);
        } else {
            String action = request.getParameter("action");
            HttpSession session = request.getSession();
            switch (action) {
                case "Login":
                    String login = request.getParameter("loginField");
                    String password = request.getParameter("pwdField");

                    AppDbHelper helper = new AppDbHelper(dbLink);
                    Employee user = null;

                    try {
                        user = helper.checkCredentials(login, password);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    if (user != null) {
                        try {
                            session.setAttribute("empList", DBObject.selectAll(dbLink, Employee.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        session.setAttribute("user", user);
                        request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
                    } else {
                        request.setAttribute("errKey", ERR_MESSAGE);
                        request.getRequestDispatcher(JSP_HOME_PAGE).forward(request, response);
                    }
                    break;
                case "Delete":
                    int id = Integer.parseInt(request.getParameter("check"));
                    try {
                        new Employee(dbLink, id).delete();
                        session.setAttribute("empList", DBObject.selectAll(dbLink, Employee.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
                    break;
                case "Details":
                    int idD = Integer.parseInt(request.getParameter("check"));

                    if (idD != 0) {
                        Employee employee = new Employee(dbLink, idD);
                        try {
                            employee.read();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        session.setAttribute("employeeChecked", employee);

                        request.getRequestDispatcher(JSP_ADD).forward(request, response);
                    } else {
                        try {
                            session.setAttribute("empList", DBObject.selectAll(dbLink, Employee.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
                    }
                    break;
                case "Add":
                    request.getRequestDispatcher(JSP_ADD).forward(request, response);
                    break;
                case "Save":
                    if (session.getAttribute("employeeChecked") != null) {
                        Employee employee = new Employee(dbLink, ((Employee)session.getAttribute("employeeChecked")).getId(), request.getParameter("inputFirstName"),
                                request.getParameter("inputLastName"), request.getParameter("inputHomePhone"),
                                request.getParameter("inputMobilePhone"), request.getParameter("inputWorkPhone"),
                                request.getParameter("inputAddress"), request.getParameter("inputPostalCode"),
                                request.getParameter("inputCity"), request.getParameter("inputEmail"), false);
                        try {
                            employee.update();
                            session.setAttribute("empList", DBObject.selectAll(dbLink, Employee.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        session.removeAttribute("employeeChecked");
                    } else {
                        Employee employee = new Employee(dbLink, request.getParameter("inputFirstName"),
                                request.getParameter("inputLastName"), request.getParameter("inputHomePhone"),
                                request.getParameter("inputMobilePhone"), request.getParameter("inputWorkPhone"),
                                request.getParameter("inputAddress"), request.getParameter("inputPostalCode"),
                                request.getParameter("inputCity"), request.getParameter("inputEmail"), false);
                        try {
                            employee.create();
                            session.setAttribute("empList", DBObject.selectAll(dbLink, Employee.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
                    break;
                case "Cancel":
                    if (session.getAttribute("employeeChecked") != null) {
                        session.removeAttribute("employeeChecked");
                    }

                    try {
                        //new Employee(dbLink, id).delete();
                        session.setAttribute("empList", DBObject.selectAll(dbLink, Employee.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
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
