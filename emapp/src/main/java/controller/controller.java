
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import static utils.Constants.*;
import model.Employee;
import model.DBActions;
/**
 *
 * @author melaniemarques
 */
public class controller extends HttpServlet {
    DBActions dba;
    InputStream input;
    String dbUrl = "";
    String dbUser = "";
    String dbPwd = "";
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
        
        if (request.getParameter("action") == null) {
            request.getRequestDispatcher(JSP_HOME_PAGE).forward(request, response);
        }
        String action = request.getParameter("action");
        
        switch(action){
            case "Login" :
                dba = new DBActions();
            
                Employee userInput = new Employee();
                userInput.setName(request.getParameter("loginField"));
                userInput.setFirstname(request.getParameter("pwdField"));
            
                if (dba.checkCredentials(userInput)) {
                    request.setAttribute("empList", dba.getEmployees());
                    HttpSession session = request.getSession();
                    session.setAttribute("user", userInput);
                    request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
                } else {
                    request.setAttribute("errKey", ERR_MESSAGE);
                    request.getRequestDispatcher(JSP_HOME_PAGE).forward(request, response);
                }
                break;
            case "Delete":
                System.out.println("we delete");
                break;
            case "Details":
                System.out.println("we ask for details");
                
                break;
            case "Add":
                System.out.println("we add");
                request.getRequestDispatcher(JSP_ADD).forward(request, response);
                break;
            case "Save":
                DBActions dba2 = new DBActions();
                Employee addEmployee = new Employee();
                addEmployee.setName(request.getParameter("inputNom"));
                addEmployee.setFirstname(request.getParameter("inputPrenom"));
                addEmployee.setHomePhone(request.getParameter("inputTelDom"));
                addEmployee.setMobilePhone(request.getParameter("inputTelMob"));
                addEmployee.setWorkPhone(request.getParameter("inputTelPro"));
                addEmployee.setAdress(request.getParameter("inputAdresse"));
                addEmployee.setPostalCode(request.getParameter("inputCodePostal"));
                addEmployee.setCity(request.getParameter("inputVille"));
                addEmployee.setEmail(request.getParameter("inputAdresseMail"));
                dba2.createEmployee(addEmployee);
                
                request.setAttribute("empList", dba2.getEmployees());
                
                request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
                
                break;
            case "Cancel":
                request.setAttribute("empList", dba.getEmployees());
                request.getRequestDispatcher(JSP_WELCOME_PAGE).forward(request, response);
            default:
                request.getRequestDispatcher(JSP_HOME_PAGE).forward(request, response);
                
        }

        }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
    }// </editor-fold>

}
